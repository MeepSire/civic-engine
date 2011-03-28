package core.events;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;

public class PhysicsForceEvent extends Event {

    private Body body;
    private Vector2f force;

    public PhysicsForceEvent(Body body, Vector2f force){
        this.body = body;
        this.force = force;
    }

    @Override
    public void execute(){
        body.addForce(force);
    }

}