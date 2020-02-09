package slr;

import java.io.IOException;

import slr.Process.Members;
import slr.Process.State;
import slr.Process.AbortMsg;
import slr.Process.DecideMsg;
import slr.Process.AckMsg;
import slr.Process.GatherMsg;
import slr.Process.ImposeMsg;

import slr.Process.ProposeMsg;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

//To print time
import java.text.SimpleDateFormat;
import java.util.Date;
//

import java.util.ArrayList;

import java.util.Collections;
import java.util.Random;

public class Main {
    private static Random rnd = new Random();
    private static long timeSarted;

    public static void main(String[] args) {
        final int N = 1000;  // The system size
        final int f = 499;
        final ActorSystem system = ActorSystem.create("system");
        Date now = new Date();
        final ArrayList<ActorRef> members = new ArrayList<ActorRef>();
        try {
            //#create-actors
            for(int x = 0; x <= N-1; x = x + 1) {
                members.add(system.actorOf(Process.props(x, N), "P"+Integer.toString(x)));
            }

            SimpleDateFormat dateFormatter = new SimpleDateFormat("E m/d/y h:m:s.SSS z");
            System.out.println("System birth: "+ dateFormatter.format(now));

            for(int x = 0; x < N; x = x + 1) {
                members.get(x).tell(new Members(members), ActorRef.noSender());
            }

            // shuffle and choose 1/3 random processes to fail

            Collections.shuffle(members);

            for(int x = 0; x < N - f; x = x + 1) {
                members.get(x).tell(new State(Process.Mode.NORMAL), ActorRef.noSender());
            }
            for(int x = N - f; x < N; x = x + 1) {
                members.get(x).tell(new State(Process.Mode.FAULTY_PRONE), ActorRef.noSender());
                //System.out.println("Process "+ members.get(x)+" is faulty");
            }

            //#main-send-messages
            for(int x = 0; x < N; x = x + 1) {
                int prop = new Random().nextInt(2);
                members.get(x).tell(new ProposeMsg(prop, false), ActorRef.noSender());
            }
            timeSarted = System.currentTimeMillis();

            Thread.sleep(500); //This is T_LE

            int leader = rnd.nextInt(N-f);
            for(int i=0; i < N ; i++){
                if(i != leader){
                    members.get(i).tell(new Process.HoldMsg(true), ActorRef.noSender());
                }
            }

            Thread.sleep(100000);

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            system.terminate();
        }
    }

}
