package world;

import java.util.Random;

/**
*   Control the enemy's behavior
*   @author Bruce Deng
*/
public class EnemyAI extends CreatureAI{

    enum EnemyState{
        IdleState,
        RandomWalkState,
        ChaseState,
        Attacking;
    }

    EnemyState eState;

    static final int idletime = 500;
    private int speed = 300;
    private int attackCD = 300;

    /**
     * 
     * @return
     * speed of enemy
     */
    public int speed(){
        return this.speed;
    }
    /**
    *   @param sp 
    *   sleep time (int) millisecond
    *   @Description Used for setting creature's sleeptime
    */
    protected void setSpeed(int sp){
        this.speed = sp;
    }

    public EnemyAI(Creature creature) {
        super(creature);
        this.creature = creature;
        this.creature.setAI(this);
        eState = EnemyState.RandomWalkState; 
    }

    /**
    *   @param t
    *   time(int, millisecond) to sleep
    *   @Description Used for Thread.sleep() in enemy's action
    */
    private void sleep(int t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onUpdate() {
        sleep(this.speed);
        switch(eState) {
            case IdleState:
                //TODO: 1.受伤后进入巡逻状态 2.发现玩家后进入追击状态
                break;
            case RandomWalkState:              
                if(findPlayer()){ //发现玩家后进入追击状态
                    System.out.println("chasing...");
                    this.eState = EnemyState.ChaseState;
                }
                else {
                    randomMove();
                }              
                break;
            case ChaseState:
                //TODO:
                
                if(findPlayer()){ 
                    Creature target = getTarget();
                    moveTo(target);
                    //attack
                    if(Math.abs(target.x()-creature.x())+Math.abs(target.y()-creature.y())<2){
                        attack(target);
                        sleep(attackCD);
                    }
                }
                else {
                    this.eState = EnemyState.RandomWalkState;//未发现玩家，进入巡逻状态
                    System.out.println("walking...");
                }    
                break;
            default:
                throw new RuntimeException("cannot process: " + eState);
        }
    }

    /**
    *   @return True if there is player in vision radius, else False
    */
    private boolean findPlayer(){
        for(Creature player : creature.getWorld().getPlayers()){
            if(canSee(player.x(), player.y())){
                return true;
            }
        }
        return false;
    }
    private Creature getTarget(){
        for(Creature player : creature.getWorld().getPlayers()){
            if(canSee(player.x(), player.y())){
                return player;
            }
        }
        return null;
    }
    class Pos{
        private final int x;
        private final int y;
        int x(){
            return this.x;
        }
        int y(){
            return this.y;
        }
        Pos(final int x, final int y){
            this.x = x;
            this.y = y;
        }
        boolean equalTo(Pos other){
            if(this.x==other.x() && this.y==other.y()){
                return true;
            }
            return false;
        }
        
    }
    /**
     * Move towards the target
     */
    private void moveTo(Creature target){
        if(target==null) return;
        //FIXME:优化算法逻辑
        //use DFS find the next move
        int sx = creature.x();
        int sy = creature.y();

        final World world = creature.getWorld();
        boolean[][] visit = new boolean[world.width()][world.height()];

        String path = new String("#");
        shortpath = "";

        DFS(new Pos(sx, sy), new Pos(target.x(), target.y()), visit, path);

        //System.out.println(shortpath);

        if(shortpath.length()>=2){
            if(shortpath.charAt(1)=='R'){//right
                creature.moveBy(1, 0);
            }
            else if(shortpath.charAt(1)=='L'){//left
                creature.moveBy(-1, 0);
            }
            else if(shortpath.charAt(1)=='D'){//down
                creature.moveBy(0, 1);
            }
            else if(shortpath.charAt(1)=='U'){//up
                creature.moveBy(0, -1);
            }
        }

    }
    private String shortpath = "";
    final void DFS(Pos p, Pos t, boolean[][] visit, String path){
        if(!isValid(p.x(), p.y()) || visit[p.x()][p.y()]){
            return;
        }
        if(shortpath.length()!=0 && path.length()>=shortpath.length()){
            return;
        }
        if(p.equalTo(t) && p!=t){//找到目标
            if(shortpath.length()==0||path.length()<shortpath.length()){
                shortpath = path;
            }
            return;
        }
        

        int nx = p.x();
        int ny = p.y();
        visit[nx][ny] = true;

        DFS(new Pos(nx-1, ny), t, visit, path+"L");//left
        DFS(new Pos(nx, ny-1), t, visit, path+"U");//up
        DFS(new Pos(nx+1, ny), t, visit, path+"R");//right
        DFS(new Pos(nx, ny+1), t, visit, path+"D");//down

        visit[nx][ny] = false;
        
        return;
    }
    final boolean isValid(int x, int y){
        final World world = creature.getWorld();
        //FIXME:要不要避开别的creature来选择路线 不用? && world.creature(x, y)==null
        if(x>=0 && y>=0 && x<world.width() && y<world.height()
            && creature.canSee(x, y)          
            && world.tile(x, y).isGround()) {
            return true;
        }

        return false;
    }


    private void randomMove(){
        //sleep before next move
        sleep(idletime);

        Random rand = new Random();
        int direction = rand.nextInt(4);
        switch(direction){
            case 0:
                creature.moveBy(-1, 0);
                break;
            case 1:
                creature.moveBy(1, 0);
                break;
            case 2:
                creature.moveBy(0, 1);
                break;
            case 3:
                creature.moveBy(0, -1);
                break;
        }
    }

    final int atk = 10;
    @Override
    public void attack(final Creature other){
        int damage = Math.max(0, this.atk - other.defenseValue());
        other.modifyHP(-damage);
    }

    public void onNotify(String message) {
    }

}