package org.usfirst.frc.team5966.robot.commands;

import org.usfirst.frc.team5966.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftUp extends Command {

	Lift lift;
	private boolean isAutonomous;
	private double speed;
	
    public LiftUp(Lift liftInstance, boolean isAutonomous) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super("LiftUp");
    	this.isAutonomous = isAutonomous;
    	this.speed = 0;
    	lift = liftInstance;
    	requires(lift);
    }
    
    public LiftUp(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super("LiftUp");
    	requires(lift);
    	this.isAutonomous = false;
    	this.speed = speed;
    }

    public void changeAutonomousMode()
    {
    	isAutonomous = !isAutonomous;
    }
    
    public void setSpeed(double speed)
    {
    	this.speed = speed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if (isAutonomous)
    	{
    		lift.liftUpSpeed(0.32);
    	}
    	else
    	{
    		lift.liftUpSpeed(speed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
