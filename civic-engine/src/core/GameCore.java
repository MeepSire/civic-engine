package core;

// CIVIC
import core.actions.ActionHandler;
import core.actions.events.PhysicsCollisionEvent;
import core.actors.*;
import core.interfaces.*;
import utils.EasyXml;
import utils.Part;

// PHYS2D
import net.phys2d.raw.strategies.QuadSpaceStrategy;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.CollisionListener;
import net.phys2d.raw.World;

// SLICK
import org.lwjgl.opengl.Display;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Input;

public class GameCore extends BasicGame implements CollisionListener {

    public static GameCore gamecore;

    private int mouseButton=-1;
    private World world;
    private ArrayList actors = new ArrayList();
    private ActionHandler handler = new ActionHandler();
    public static int width;
    public static int height;

    private static GameContainer ctr;

    public GameCore() {
        super("CiviC Game Core");
        this.gamecore = this;
        setIcon("images/iconC.png");
        //setIcon(new String[]{"images/128.png","images/32.png","images/16.png"});
        new EasyXml("test");
    }

    public ActionHandler getActionHandler(){
        return handler;
    }

    public void addActor(Actor actor){
        actors.add(actor);
        if(actor instanceof PhysicsActor == true){
            PhysicsActor phActor = (PhysicsActor) actor;
            world.add(phActor.getBody());
        }
    }

    public void removeActor(Actor actor){
        actors.remove(actor);
        if(actor instanceof PhysicsActor) {
            PhysicsActor physicsActor = (PhysicsActor) actor;
            world.remove(physicsActor.getBody());
        }
        actor = null;
    }

    @Override
    public void init(GameContainer container) throws SlickException {

        //container.setMinimumLogicUpdateInterval(25);
        container.setMaximumLogicUpdateInterval(100);

        // MUST FIRST CREATE THE WORLD BEFORE ADDING ACTORS!
        world = new World(new Vector2f(0, (float) 500.0), 10, new QuadSpaceStrategy(20,5));

        this.world.addListener(gamecore);
        container.getInput().addListener(this);
        ctr = container;

        width = container.getWidth();
        height = container.getHeight();

        new Mario(width/2, 100);

        for(int x = width/3; x <= (width/3) * 2; x += 16){
            new ItemBox((float)x, height - 100);
            x += 0;
        }

        //new Updater(this);

    }

    @Override
    public void keyPressed(int i, char c){
        if(c == 'i'){
            try {
                new EmptyItemBox(100f, 100f);
            } catch (SlickException ex) {
            }
        }
    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {
        if(mouseButton==0){

            try {
                new EmptyItemBox(i + 8, i1 + 8);
            } catch (SlickException ex) {
                Logger.getLogger(Mario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void mouseReleased(int i, int i1, int i2){
        mouseButton = -1;
    }

    @Override
    public void mousePressed(int i, int i1, int i2) {
        mouseButton = i;
        if(i==0){

            try {
                new EmptyItemBox(i1 + 8, i2 + 8);
            } catch (SlickException ex) {
                Logger.getLogger(Mario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if(i==1){

            try {
                new ItemBox((int)((i1+8)/16)*16, (int)((i2+8)/16)*16);
            } catch (SlickException ex) {
                Logger.getLogger(Mario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if(i==2){

            try {
                new Mario((int)((i1/16)*16), (int)((i2+8)/16)*16);
            } catch (SlickException ex) {
                Logger.getLogger(Mario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        ctr = container;

        // NETWORKING
        // TODO: Networking

        // PHYSICS
        world.step();

        // EXECUTE TRIGGERED ACTIONS
        handler.executeActions();

        // ACT
        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i) instanceof PhysicsActor){
                if(((PhysicsActor)actors.get(i)).getBody().getPosition().distance(new Vector2f(0,0)) > 10000){
                    ((PhysicsActor)actors.get(i)).delete();
                }
            }
            if(actors.get(i) instanceof Drawable){
                Actor actor = (Actor)actors.get(i);
                actor.act(container.getInput());
                if (actor.isDeleteable()){
                    removeActor(actor);
                }
            }
        }

        globalInputAction(container.getInput());

    }

    protected void globalInputAction(Input input) throws SlickException{

        
        /*
        if(input.isKeyPressed(Input.KEY_S)){
            save("C:\\test.xml",true);
        }
        if(input.isKeyPressed(Input.KEY_L)){
            load("C:\\test.xml",true);
        }
        */

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {

        ctr = container;

        g.drawString("MOVE WITH LEFT-RIGHT, JUMP WITH SPACE", 10, 30);
        g.drawString("N: RESET POSITION", 10, 50);
        g.drawString("MOUSE KLICK: NEW BOX", 10, 70);

        ArrayList phslst = new ArrayList();

        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i) instanceof Drawable){
                Drawable drw = (Drawable)actors.get(i);
                drw.draw(g);
                if(actors.get(i) instanceof Mario){
                    Mario mario = (Mario)actors.get(i);
                    g.drawString("POSITION: " + Math.round(mario.getBody().getPosition().getX()) + ", " + Math.round(mario.getBody().getPosition().getY()), 10, 110);
                    g.drawString("VELOCITY: " + Math.round(mario.getBody().getVelocity().getX()) + ", " + Math.round(mario.getBody().getVelocity().getY()), 10, 130);
                }
                if(actors.get(i) instanceof PhysicsActor){
                    phslst.add(actors.get(i));
                }
            }
        }
        g.drawString("Physics Actors: " + phslst.size(), 10, 150);
        phslst = null;
    }

    public World getWorld(){
        return world;
    }

    public Actor[] getActors(){
        Actor[] actor = new Actor[actors.size()];
        for(int i = 0; i < actor.length; i++){
            actor[i] = (Actor)actors.get(i);
        }
        return actor;
    }

    // START
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new GameCore());
            app.setDisplayMode(640, 480, false);
            //app.setVSync(false);
            app.start();
            //app.setTargetFrameRate(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void collisionOccured(CollisionEvent evt) {
        new PhysicsCollisionEvent(evt).trigger();
    }

    private class Timer {
        
        private long startTime;
        
        public Timer(){
        }
        
        public void start(){
            startTime = System.currentTimeMillis();
        }
        
        public long stop(){
            return System.currentTimeMillis() - startTime;
        }
        
    }

    public void load(String url,boolean physics) throws SlickException{
        System.out.println("loading");
        for(int i=0;i<actors.size();i++){
            ((Actor)actors.get(i)).delete();
        }
        File save = new File(url);
        int count = (EasyXml.find(save, new String[]{"save"})).length;
        for(int i=0; i< count;i++){
            Actor ac = new ItemBox(Float.parseFloat(EasyXml.find(save, new String[]{"x"})[i]),Float.parseFloat(EasyXml.find(save, new String[]{"y"})[i]));
            String force = EasyXml.find(save, new String[]{"force"})[i];
            String x="";
            String y="";
            int j=0;
            while(force.charAt(j)!=','){
                x+=force.charAt(j);
                j++;
            }
            if(force.charAt(j)==' ')j++;
            while(j<force.length()){
                y+=force.charAt(j);
                j++;
            }
            //if(physics)((PhysicsActor)ac).getBody().addForce(new Vector2f(Float.parseFloat(x),Float.parseFloat(y)));
        }
    }

    public boolean save(String url,boolean physics){
        System.out.println("saving");
        EasyXml save = new EasyXml("save");
        for(int i=0;i<actors.size();i++){
            String name = ((Actor)actors.get(i)).toString();
            String str = "";
            int j=0;
            System.out.println("creating name");
            while(name.charAt(j)!='@'&&j<name.length()){
                str += name.charAt(j);
                //System.out.println(j+"");
                j++;
            }
            Part actor = save.addPart(str);
            System.out.println("add Parts to "+str);
            actor.addPart("x", ((Actor)actors.get(i)).x+"");
            actor.addPart("y", ((Actor)actors.get(i)).y+"");
            System.out.println("add Force");
            if(physics)actor.addPart("force", ((PhysicsActor)actors.get(i)).getBody().getForce().getX()+", "+((PhysicsActor)actors.get(i)).getBody().getForce().getY());
            System.out.println("saved "+str);
        }
        return save.save(url);
    }
/**
 * Sets the game and Tray Icon
 * @param urls path to the 3 icons(as images): String[]{128x128 imageurl, 32x32 imageurl, 16x16 imageurl}
 */
    private void setIcon(String[] urls) //String[]{128x128 imageurl, 32x32 imageurl, 16x16 imageurl}
    {
        try{
            Display.setIcon(loadIcon(urls));
        }catch(Exception e){
            System.err.println("Error[0]:failed to set icon!");
        }
    }
/**
 * Sets the game and Tray Icon
 * @param url path to one image of which the 3 differently sized icons are created automatically
 */
    private void setIcon(String url) //128x128 image, 32x32 image, 16x16 image created from one imageurl
    {
        setIcon(new String[] {url});
    }

    public static ByteBuffer[] loadIcon(String[] filepath)
	{
            ByteBuffer[] buffers = new ByteBuffer[3];
            if(filepath.length==1){
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File(filepath[0]));
		}
		catch (IOException e)
		{
			System.err.println("Error[1]:couldnt load icon image "+filepath[0]);
		}
		buffers[0] = loadIconInstance(image, 128);
		buffers[1] = loadIconInstance(image, 32);
		buffers[2] = loadIconInstance(image, 16);
		return buffers;
            }else{
                BufferedImage image0 = null;
                BufferedImage image1 = null;
                BufferedImage image2 = null;
                try
		{
			image0 = ImageIO.read(new File(filepath[0]));
                        image1 = ImageIO.read(new File(filepath[1]));
                        image2 = ImageIO.read(new File(filepath[2]));
		}
		catch (IOException e)
		{
			System.err.println("Error[1]:couldnt load icon image "+filepath[0]);
		}
		buffers[0] = loadIconInstance(image0, 128);
		buffers[1] = loadIconInstance(image1, 32);
		buffers[2] = loadIconInstance(image2, 16);
                return buffers;
            }
	}

	private static ByteBuffer loadIconInstance(BufferedImage image, int dimension)
	{
		BufferedImage scaledIcon = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = scaledIcon.createGraphics();
		double ratio = 1;
		if(image.getWidth() > scaledIcon.getWidth())
		{
			ratio = (double) (scaledIcon.getWidth()) / image.getWidth();
		}
		else
		{
			ratio = (int) (scaledIcon.getWidth() / image.getWidth());
		}
		if(image.getHeight() > scaledIcon.getHeight())
		{
			double r2 = (double) (scaledIcon.getHeight()) / image.getHeight();
			if(r2 < ratio)
			{
				ratio = r2;
			}
		}
		else
		{
			double r2 =  (int) (scaledIcon.getHeight() / image.getHeight());
			if(r2 < ratio)
			{
				ratio = r2;
			}
		}
		double width = image.getWidth() * ratio;
		double height = image.getHeight() * ratio;
		g.drawImage(image, (int) ((scaledIcon.getWidth() - width) / 2), (int) ((scaledIcon.getHeight() - height) / 2),
				(int) (width), (int) (height), null);
		g.dispose();

		byte[] imageBuffer = new byte[dimension*dimension*4];
		int counter = 0;
		for(int i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				int colorSpace = scaledIcon.getRGB(j, i);
				imageBuffer[counter + 0] =(byte)((colorSpace << 8) >> 24 );
				imageBuffer[counter + 1] =(byte)((colorSpace << 16) >> 24 );
				imageBuffer[counter + 2] =(byte)((colorSpace << 24) >> 24 );
				imageBuffer[counter + 3] =(byte)(colorSpace >> 24 );
				counter += 4;
			}
		}
		return ByteBuffer.wrap(imageBuffer);
	}

        private class Updater extends Thread implements Runnable{
            private GameCore gc;
            public Updater(GameCore gc){
                this.gc = gc;
                this.start();
            }
        @Override
            public void run(){
                while(true){
                    try {
                        gc.update(gc.ctr, 10);
                        //gc.world.step();
                    } catch (SlickException ex) {
                    }
                    try {
                        sleep(10L);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }

}