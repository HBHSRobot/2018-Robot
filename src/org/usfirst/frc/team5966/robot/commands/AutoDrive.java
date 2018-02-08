package org.usfirst.frc.team5966.robot.commands;

import org.usfirst.frc.team5966.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command{

	int timer;
	boolean leftPosition;
	final double BASE_SPEED = -0.32;
	double speed;
	DriveTrain drivetrain = new DriveTrain();
	boolean forwards, backwards;
	
	public AutoDrive()
	{
		requires(drivetrain);
	}

	protected void initialize()
	{
		
	}
	
	protected void execute()
	{
		drivetrain.forwardDrive(speed, 0);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
