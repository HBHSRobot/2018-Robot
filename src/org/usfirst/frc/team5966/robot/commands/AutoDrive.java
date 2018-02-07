package org.usfirst.frc.team5966.robot.commands;

import org.usfirst.frc.team5966.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command{

	String gameData;
	boolean rightPosition = false;
	DriveTrain drivetrain = new DriveTrain();
	
	public AutoDrive()
	{
		requires(drivetrain);
	}

	protected void initialize()
	{
		gameData = DriverStation.getInstance().getGameSpecificMessage();

	}
	
	protected void execute()
	{
		if(rightPosition) {
			if(gameData.charAt(0) == 'L')
			{
			//	drivetrain.forwardDrive(0.32, 0, false);
			} else {
			//	drivetrain.forwardDrive(0.32, 0, false);
			}
		}else{
			if(gameData.charAt(0) == 'L')
			{
				
			} else {
				
			}
		}
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
