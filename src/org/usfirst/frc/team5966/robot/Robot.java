/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5966.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

import org.usfirst.frc.team5966.robot.commands.*;
import org.usfirst.frc.team5966.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot 
{
	public enum StartingPosition
	{
		LEFT, MIDDLE, RIGHT
	}
	
	public static OI oi;
	CameraServer cameraServer;

	String gameData;
	
	Grabber grabber;
	DriveTrain drivetrain;
	Lift lift;
	
	DriveForwards driveForwards;
	DriveBackwards driveBackwards;
	LiftUp liftUp;
	LiftDown liftDown;
	StopLift stopLift;
	GrabberOpen grabberOpen;
	GrabberClose grabberClose;
	GrabberAuto grabberAuto;
	SendableChooser<StartingPosition> startingPositionChooser;

	AnalogInput sensor;
	double volts;
	
	boolean autoDriveFinished;
	
	final int RIGHT_TRIGGER_AXIS = 3;
	final int LEFT_TRIGGER_AXIS = 2;
	final double LIFT_DEAD_SPOT = 0.25;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() 
	{
		oi = new OI();
		//Chooser for the robot's starting position on the dashboard
		startingPositionChooser =  new SendableChooser<StartingPosition>();
		startingPositionChooser.addObject("Left", StartingPosition.LEFT);
		startingPositionChooser.addDefault("Middle", StartingPosition.MIDDLE);
		startingPositionChooser.addObject("Right", StartingPosition.RIGHT);
		SmartDashboard.putData("Starting Position", startingPositionChooser);
		
		autoDriveFinished = false;
		
		grabber = oi.getGrabber();
		drivetrain = oi.getDriveTrain();
		lift = new Lift();
		
		grabberOpen = new GrabberOpen(grabber);
		grabberClose = new GrabberClose(grabber);
		driveForwards = new DriveForwards(drivetrain, true);
		liftUp = new LiftUp(lift, true);
		stopLift = new StopLift(lift);
		
		//proximity sensor
		sensor = new AnalogInput(0);
		
		//Camera Server
		cameraServer = CameraServer.getInstance();
		UsbCamera camera = new UsbCamera("HudsonCam", "/dev/video0");
		cameraServer.addCamera(camera);
		cameraServer.startAutomaticCapture(camera);
		System.out.println("Robot Initialization Complete");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() 
	{

	}

	@Override
	public void disabledPeriodic() 
	{
		Scheduler.getInstance().run();
	}
	

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	public void autonomousInit() 
	{
		grabberAuto = new GrabberAuto(grabber);
		//schedule the autonomous command (example)
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		//gathers the data from the dashboard on the position of the robot
		//if the robot is in the same position as the switch as reported by LabView, then it will lift up
		grabberAuto.start();
		switch(startingPositionChooser.getSelected())
		{
			case LEFT:
				if((gameData.charAt(0) == 'L') && (liftUp != null)) 
				{
					liftUp.start();
				}
				break;
			case RIGHT:
				if((gameData.charAt(0) == 'R') && (liftUp != null)) 
				{
					liftUp.start();
				}
				break;
		}
		driveForwards.start();
		/* I moved the switch logic to autoPeriodic because the sensor updates through periodic, and only
		 * when the drive is stopped, based on the sensor, is the lift going to operate
		 */
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
		//gets the voltage from the sensor, then divides it by 9.766, our scaling factor
		double distance = sensor.getVoltage() / 9.766;
		//2.75 is a placeholder range for the sensor in inches, change to whatever is actually needed
		if(distance >= 2.75) 
		{
			if (grabberAuto != null)
			{
				grabberAuto.cancel();
			}
			grabberOpen.start();
			if (driveForwards != null)
			{
				driveForwards.cancel();
			}
		}
		//just for testing to check that the reading in the program corresponds with the reading from LabView
		System.out.println(volts);
	}

	@Override
	public void teleopInit() 
	{
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		driveBackwards = new DriveBackwards(drivetrain);
		liftDown = new LiftDown(lift);
		if (driveForwards != null) 
		{
			driveForwards.changeAutonomousMode();
		}
		if (liftUp != null)
		{
			liftUp.changeAutonomousMode();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() 
	{
		System.out.println("Teleop Periodic");
		Scheduler.getInstance().run();
		double rTrigger = oi.getXbox().getRawAxis(RIGHT_TRIGGER_AXIS);
		double lTrigger = oi.getXbox().getRawAxis(LEFT_TRIGGER_AXIS);
		if(rTrigger > 0 && lTrigger <= 0)
		{
			System.out.println("Drive Forwards");
			if(driveForwards != null)
			{
				driveForwards.start();
			}
			if(driveBackwards != null)
			{
				driveBackwards.cancel();
			}
			driveForwards.setSpeed(rTrigger);
			driveForwards.setRotation(oi.getXbox().getRawAxis(0));
		}
		else if(lTrigger > 0 && rTrigger <= 0)
		{
			System.out.println("Drive Backwards");
			if(driveBackwards != null)
			{
				driveBackwards.start();
			}
			if(driveForwards != null)
			{
				driveForwards.cancel();
			}
			driveBackwards.setSpeed(lTrigger);
			driveBackwards.setRotation(oi.getXbox().getRawAxis(0));
		}
		else
		{
			System.out.println("Stop Drive");
			if(driveForwards != null)
			{
				driveForwards.cancel();
			}
			if(driveBackwards != null)
			{
				driveBackwards.cancel();
			}
		}
		
		double rightY = oi.getXbox().getRawAxis(5);
		System.out.println("Right Analog Stick Y Axis: " + rightY);
		if (rightY > LIFT_DEAD_SPOT)
		{
			if (liftDown != null)
			{
				liftDown.setSpeed(0);
				liftDown.cancel();
			}
			if (liftUp != null)
			{
				liftUp.start();
				liftUp.setSpeed(rightY);
			}
		}
		else if (rightY < -1 * LIFT_DEAD_SPOT)
		{
			if (liftUp != null)
			{
				liftUp.setSpeed(0);
				liftUp.cancel();
			}
			if (liftDown != null)
			{
				liftDown.start();
				liftDown.setSpeed(rightY);
			}
		}
		else
		{
			System.out.println("Stop Lift");
			if (liftUp != null)
			{
				liftUp.setSpeed(0);
				liftUp.cancel();
				lift.liftStop();
			}
			if (liftDown != null)
			{
				liftDown.setSpeed(0);
				liftDown.cancel();
				lift.liftStop();
			}
			if (stopLift != null)
			{
				stopLift.start();
				stopLift.cancel();
				lift.liftStop();
			}
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
