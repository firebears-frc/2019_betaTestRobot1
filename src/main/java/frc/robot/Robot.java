/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Simple program for our roboRIO test board.
 * There are two TalonSRXs attached, with CAN IDs of 2 and 4.
 */
public class Robot extends TimedRobot {

	public static boolean DEBUG = true;
	private XboxController xboxController;
	private WPI_TalonSRX leftMotor;
	private WPI_TalonSRX rightMotor;
	Timer autonomousTimer;

	@Override
	public void robotInit() {
		xboxController = new XboxController(0);
		leftMotor = new WPI_TalonSRX(2);
		rightMotor = new WPI_TalonSRX(4);
		// leftMotor.setExpiration(30);
		// rightMotor.setExpiration(30);
		autonomousTimer = new Timer();
		if (DEBUG) System.out.println("robotInit");
	}

	@Override
	public void robotPeriodic() {
	}

	@Override
	public void teleopInit() {
		if (DEBUG) System.out.println("teleopInit");
	}

	@Override
	public void teleopPeriodic() {
		double leftSpeed = -1 * xboxController.getY(Hand.kLeft);
		double rightSpeed = -1 * xboxController.getY(Hand.kRight);
		boolean slowMode = xboxController.getBumper(Hand.kRight);
		if (slowMode) {
			leftSpeed = leftSpeed * 0.5;
			rightSpeed = rightSpeed * 0.5;
		}
		leftMotor.set(leftSpeed);
		rightMotor.set(rightSpeed);
		if (DEBUG) System.out.println("teleopPeriodic: "+ slowMode + "  " + leftSpeed + ", " + rightSpeed); 
	}

	@Override
	public void autonomousInit() {
		autonomousTimer.reset();
		autonomousTimer.start();
		leftMotor.set(0.5);
		rightMotor.set(0.5);
		if (DEBUG) System.out.println("autnomousInit");
	}

	@Override
	public void autonomousPeriodic() {
		double currentTime = autonomousTimer.get();
		if (currentTime > 5.0) {
			leftMotor.set(0.0);
			rightMotor.set(0.0);
			autonomousTimer.stop();
			if (DEBUG) System.out.println("teleopPeriodic: stopped");
		} else {
			leftMotor.set(0.5);
			rightMotor.set(0.5);
			if (DEBUG) System.out.println("teleopPeriodic: MOVING");
		}

	}

	@Override
	public void disabledInit() {
		leftMotor.set(0.0);
		rightMotor.set(0.0);
		if (DEBUG) System.out.println("disabledInit");
	}
}
