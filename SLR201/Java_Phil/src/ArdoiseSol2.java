public class ArdoiseSol2 {
    public enum States {AFFAME, PENSER, MANGER}
    private States [] state;
    private int num;

    public ArdoiseSol2(){
        num = 5;
        state = new States[num];
        for(int i = 0; i < num; i++) state[i] = States.PENSER;
    }

    public void setArdoise(int PhiloID, States next_state){
        state[PhiloID] = next_state;
    }

    private int findPoriority(){
        int pp = num;
        for(int i = 0; i < num; i++ ){
            if(state[i] == States.AFFAME) {
                if ((i < num - 1) && (i != 0)) {
                    if ((state[i - 1] == States.PENSER) && (state[i + 1] == States.PENSER)) {
                        pp = i;
                        break;
                    }
                }
                if ((i == num - 1) && (state[i - 1] == States.PENSER) && (state[0] == States.PENSER)) {
                    pp = i;
                    break;
                }
                if ((i == 0) && (state[num - 1] == States.PENSER) && (state[1] == States.PENSER)) {
                    pp = i;
                    break;
                }
            }
        }
        return pp;
    }

    public boolean checkArdoise(int PhiloID){
        int p = findPoriority();
       /* int j = 0;
        for(int i = 0; i < num; i++ ){
            if (state[i] == States.PENSER) {
                j++;
            }
        }
        if (j == 5) {
            for (int d = PhiloID; d < num - 1; d+=2) {
                setArdoise(d, States.AFFAME);
            }
        }*/
        if(p == PhiloID){
            return true;
        } else {
            if(p < num){
                return false;
            } else{
                if((PhiloID < num - 1) && (PhiloID != 0)) {
                    if((state[PhiloID] == States.AFFAME) && (state[PhiloID - 1] != States.MANGER) && (state[PhiloID + 1] != States.MANGER)){
                        return true;
                    }
                }
                if((PhiloID == num - 1) && (state[PhiloID] == States.AFFAME) && (state[PhiloID - 1] != States.MANGER) && (state[0] != States.MANGER)) return true;
                if((PhiloID == 0) && (state[PhiloID] == States.AFFAME) && (state[num - 1] != States.MANGER) && (state[1] != States.MANGER)) return true;
            }
        }
        return false;
    }

}
