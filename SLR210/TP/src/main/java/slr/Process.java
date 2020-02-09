package slr;

import akka.actor.UntypedAbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import akka.event.Logging;
import akka.event.LoggingAdapter;


import java.util.ArrayList;
import java.util.Random;


//#greeter-messages
public class Process extends UntypedAbstractActor {
	//private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private LoggingAdapter log = null;
	private final int N;
	private final int id;
	private Mode mode;
	private int ballot;
	private int proposal;
	private int readBallot;
	private int imposeBallot;
	private int estimate;
	private int statesCount;
	private int NoP;
	private Members processes;
	private boolean isHolding;
	private long timeStarted;

	ArrayList<Pair> states;

	//static public Props props() {
	//  return Props.create(Process.class, () -> {
	//			return new Process();
	//  });
	//}

	static public Props props(int id, int N) {
		return Props.create(Process.class, () -> new Process(id, N));
	}

	private void uponPropose(Integer v, boolean re) {
		proposal = v;
		ballot += N;
		states = new ArrayList();
		this.NoP++;
		for(int x = 0; x < N; x++) {
			states.add(new Pair(-1, 0));
		}
		for (ActorRef actor : processes.members) {
			actor.tell(new ReadMsg(ballot), this.getSelf());
		}
		//log.info(self().path().name() + " proposed " + this.proposal + ", try: " + NoP);
	}

	private void uponRead(int newBallot, ActorRef pj) {
		if (readBallot >= newBallot || imposeBallot >= newBallot) {
			pj.tell(new AbortMsg(newBallot), this.getSelf());
			//log.info(self().path().name() + " told " + pj.path().name() + " to give up proposal " + this.proposal);
		} else {
			readBallot = newBallot;
			pj.tell(new GatherMsg(newBallot, imposeBallot, estimate), this.getSelf());
			//log.info(self().path().name() + " told " + pj.path().name() + " to count proposal " + this.proposal);
		}
	}

	private void uponAbort(int ballot) throws InterruptedException {
		log.info(self().path().name() + " aborted ballot: " + ballot);

		//Thread.sleep(100);
		//if(this.isHolding == false) {
		//	int prop = new Random().nextInt(2);
		//	self().tell(new ProposeMsg(prop, true), ActorRef.noSender());
		//}
	}

	private void uponGather(int ballot, int estballot, int est, ActorRef pj) {
		this.states.set(Integer.parseInt(pj.path().name().substring(1)), new Pair(est, estballot));
		this.statesCount += 1;

		if (statesCount > N / (2 * 1.0)) {
			Pair maxPair = new Pair(-1,0);
			for (Pair tuple : states) {
				if(maxPair.x == -1 && maxPair.y == 0) {
					maxPair = tuple;
					continue;
				}
				if (!(tuple.x == -1) && !(tuple.y == 0) && tuple.y >= maxPair.y) {
					maxPair = tuple;
				}
			}
			if (maxPair.x != -1 && maxPair.y > 0) {
				proposal = maxPair.x;
			}

				states = new ArrayList();

				for (int x = 0; x < N; x++) {
					states.add(new Pair(-1, 0));
				}

				for (ActorRef actor : processes.members) {
					actor.tell(new ImposeMsg(ballot, proposal), this.getSelf());
				}
				//log.info(self().path().name() + " imposed proposal " + proposal);

		}
	}

	private void uponImpose(int newBallot, Integer v, ActorRef pj) {
		if (readBallot > newBallot || imposeBallot > newBallot) {
			pj.tell(new AbortMsg(newBallot), this.getSelf());
		} else {
			estimate = v;
			imposeBallot = newBallot;
			pj.tell(new AckMsg(newBallot), this.getSelf());
		}
	}

	int acksReceived = 0;

	private void uponAck(int ballot, ActorRef pj) {
		acksReceived++;
		if (acksReceived > N / 2) {
			for (ActorRef actor : processes.members) {
				actor.tell(new DecideMsg(proposal), this.getSelf());
			}
		}
	}

	private void uponDecide(int v, ActorRef pj) throws InterruptedException {
		for (ActorRef actor : processes.members) {
			actor.tell(new DecideMsg(v), this.getSelf());
		}
		log.info(self().path().name() + " decided on value: " + v);
		long timeDecision = System.currentTimeMillis();
		long timeUsed = timeDecision - this.timeStarted;
		log.info("Time taken:"+ timeUsed);
		this.mode = Mode.SILENT;
	}

	private void uponHold(boolean hold) {
		this.isHolding = hold;
		log.info(self().path().name() + " held.");
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(this.mode == Mode.FAULTY_PRONE) {
			double prob = 0.3;
			double rdm = new Random().nextDouble();

			if(rdm > prob) {
				this.mode = Mode.SILENT;
				log.info(self().path().name() + " quit the group.");
			}

		}
		if (this.mode != Mode.SILENT) { // If not faulty
			ActorRef actorRef = getSender();

			if (message instanceof Members) {
				Members m = (Members) message;
				processes = m;
				log.info(self().path().name() + " joined the group");

			} else if (message instanceof State){
				this.mode = ((State) message).mode;
				if (this.mode == Mode.NORMAL) { // the process is active
					log.info("P"+this.id+" is active");
				}
				else { // the process is faulty
					log.info("P"+this.id+" is faulty");
				}
			} else if (message instanceof ProposeMsg) {
				ProposeMsg m = (ProposeMsg) message;
				this.uponPropose(m.v, m.re);
			} else if (message instanceof ReadMsg) {
				ReadMsg m = (ReadMsg) message;
				this.uponRead(m.ballot, getSender());
			} else if (message instanceof AbortMsg) {
				AbortMsg m = (AbortMsg) message;
				this.uponAbort(m.ballot);
			} else if (message instanceof GatherMsg) {
				GatherMsg m = (GatherMsg) message;
				this.uponGather(m.ballot, m.imposeBallot, m.estimate, getSender());
			} else if (message instanceof ImposeMsg) {
				ImposeMsg m = (ImposeMsg) message;
				this.uponImpose(m.ballot, m.proposal, getSender());
			} else if (message instanceof AckMsg) {
				AckMsg m = (AckMsg) message;
				this.uponAck(m.ballot, getSender());
			} else if (message instanceof DecideMsg) {
				DecideMsg m = (DecideMsg) message;
				this.uponDecide(m.proposal, getSender());
			} else if (message instanceof HoldMsg) {
				this.uponHold(((HoldMsg) message).hold);
			}
		} else {
			unhandled(message);
		}

	}

	//#system-members
	static public class Members {
		//public final int num;
		public final ArrayList<ActorRef> members;

		public Members(ArrayList<ActorRef> members) {
			//    this.num = members.size();
			this.members = members;
		}
	}


	public Process(int id, int N) {
		this.id = id;
		this.N = N;
		this.ballot = id - N;
		this.proposal = -1;
		this.readBallot = 0;
		this.imposeBallot = id - N;
		this.estimate = -1;
		this.states = new ArrayList();
		this.statesCount = 0;
		this.isHolding = false;
		this.NoP = 0;
		this.timeStarted = System.currentTimeMillis();
		for(int x = 0; x < N; x++) {
			states.add(new Pair(-1, 0));
		}
		this.mode = Mode.NORMAL;
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		log = Logging.getLogger(getContext().getSystem(), this);
	}

	static public class State {
		public final Mode mode;

		public State(Mode mode) {
			this.mode = mode;
		}
	}

	private static class Pair {

		public final int x;
		public final int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static public class AbortMsg {
		int ballot;

		public AbortMsg(int ballot) {
			this.ballot = ballot;
		}

	}

	static public class AckMsg {
		int ballot;

		public AckMsg(int ballot) {
			this.ballot = ballot;
		}

	}

	static public class DecideMsg {
		int proposal;

		public DecideMsg(int proposal) {
			this.proposal = proposal;
		}

	}

	static public class GatherMsg {
		int ballot;
		int imposeBallot;
		int estimate;

		public GatherMsg(int ballot, int imposeBallot, int estimate) {
			this.ballot = ballot;
			this.imposeBallot = imposeBallot;
			this.estimate = estimate;
		}
	}

	static public class ImposeMsg {
		int ballot;
		Integer proposal;

		public ImposeMsg(int ballot, Integer proposal) {
			this.ballot = ballot;
			this.proposal = proposal;
		}

	}

	static public class ReadMsg {
		int ballot;

		public ReadMsg(int ballot) {
			this.ballot = ballot;
		}
	}

	static public class ProposeMsg {
		int v;
		boolean re;

		public ProposeMsg(int v, boolean re) {
			this.v = v;
			this.re = re;
		}

	}

	static public class HoldMsg {

		boolean hold;

		public HoldMsg(boolean hold){
			this.hold = hold;
		}
	}


	public enum Mode {
		FAULTY_PRONE, SILENT, NORMAL, STOP;
	}

}

