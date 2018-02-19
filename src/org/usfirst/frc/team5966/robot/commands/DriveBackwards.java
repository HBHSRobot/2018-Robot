package org.usfirst.frc.team5966.robot.commands;

import org.usfirst.frc.team5966.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveBackwards extends Command {

	DriveTrain drivetrain = new DriveTrain();
	
	private double speed, rotation;
	
    public DriveBackwards() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super("DriveBackwards");
    	requires(drivetrain);
    	this.speed = 0;
    	this.rotation = 0;
    }

    public void setSpeed(double speed)
    {
    	this.speed = speed;
    }
    
    public void setRotation(double rotation)
    {
    	this.rotation = rotation;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.reverseDrive(speed, rotation);
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
