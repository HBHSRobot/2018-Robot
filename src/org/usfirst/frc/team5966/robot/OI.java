/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5966.robot;

import org.usfirst.frc.team5966.robot.commands.*;
import org.usfirst.frc.team5966.robot.subsystems.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	private Grabber grabber;
	private DriveTrain driveTrain;
	private Joystick xbox;
	private Button buttonA;
	private Button buttonB;
	private Button buttonLeftStickButton;

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	public OI()
	{
		grabber = new Grabber();
		driveTrain = new DriveTrain();
		
		xbox = new Joystick(0);
		buttonA = new JoystickButton(xbox, 1);
		buttonB = new JoystickButton(xbox, 2);
		buttonLeftStickButton = new JoystickButton(xbox, 9);
		
		buttonA.whileHeld(new GrabberOpen(grabber));
		buttonB.whileHeld(new GrabberClose(grabber));
		buttonLeftStickButton.whenPressed(new EnableReduction(driveTrain));
		buttonLeftStickButton.whenReleased(new DisableReduction(driveTrain));
	}

	public Joystick getXbox()
	{
		return xbox;
	}
	
	public Grabber getGrabber()
	{
		return grabber;
	}
	
	public DriveTrain getDriveTrain()
	{
		return driveTrain;
	}

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
