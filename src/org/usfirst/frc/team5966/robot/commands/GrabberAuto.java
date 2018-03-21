package org.usfirst.frc.team5966.robot.commands;

import org.usfirst.frc.team5966.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GrabberAuto extends Command {

	Grabber grabber;
	double speed;
	boolean finished = false;
	
    public GrabberAuto(Grabber grabberInstance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super("GrabberAuto");
    	grabber = grabberInstance;
    	requires(grabber);
    	speed = 0.32;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	grabber.grabberForwards(speed);
    	//in seconds
    	setTimeout(1.5);
    	grabber.grabberBackwards(speed);
    	finished = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (finished)
    		grabber.stopMotors();
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
