package org.usfirst.frc.team5966.robot.commands;

import org.usfirst.frc.team5966.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftElevate extends Command {

	Lift lift = new Lift();
	
    public LiftElevate() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	lift.liftUp(0.32);
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
