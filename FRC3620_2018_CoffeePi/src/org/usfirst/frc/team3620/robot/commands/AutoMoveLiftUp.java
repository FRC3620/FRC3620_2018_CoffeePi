package org.usfirst.frc.team3620.robot.commands;

import org.usfirst.frc.team3620.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.slf4j.Logger;
/**		WELCOME to the Auto Lift Commands Choose Your Own Debug Book. If you need to debug something or desire
 * 	enlightenment as to what the heck something might mean, follow the helpful comments nearby. They will point
 * 	you in the right direction.
 *
 */
public abstract class AutoMoveLiftUp extends Command {
	/* These are all of the variables that we define so we can be fully configurable. All of these variables
	 * are required so that the specific hyperbolic function used by the robot to send percent output can be
	 * calculated in the Lift Subsystem (line 177).
	 * 
	 * oneFoot is the number of tics that is roughly equivalent to 1 foot, a good distance for slowing down
	 * and such, which is why the speedUp point 
	 * 
	 * The startingEncoderPos is helpful for being able to calculate the k for the hyperbolic function as one
	 * can see in the Lift Subsystem (line 177).
	 * 
	 * encoderPos is just the encoder position read every execute. The requestedEncoderPos is the final destination.
	 * The desiredStartingPower and desiredEndingPower are useful in the calculation of the k.
	*/
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);
	double startingEncoderPos;
	double requestedEncoderPos; //  TODO was 4700, changed it for testing purposes
	//1 revolution = 3 inches
	double oneFoot;
	double slowDownPoint;
	double speedUpPoint;
	double desiredStartingPower;
	double maxPower;
	double desiredEndingPower = Robot.liftSubsystem.bracingVoltage + 0.15;
	
	boolean weAreDoneSenor = false;
    public AutoMoveLiftUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftSubsystem);
    	
    }
    
    
    public abstract double getRequestedEndPos();
    public double getMaxPower() {
    	return 0.6;
    }
    
    public double getStartingPower() {
    	return 0.35;
    }
    
    public double getAccelDecelDistance() {
    	return 9;
    }
    // Called just before this Command runs the first time
    //1440 ticks of encoder = 16.875 inches
    //TO-DO ADD EXPERIMENTAL VALUES TO INITIALIZE THE VARIABLES
    protected void initialize() {
    	logger.info("Starting AutoMoveLiftUp Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    	startingEncoderPos = Robot.liftSubsystem.readEncoderInInches();
    	weAreDoneSenor = false;
    	desiredStartingPower = getStartingPower();
    	oneFoot = getAccelDecelDistance();
    	maxPower = getMaxPower();
    	requestedEncoderPos = getRequestedEndPos();
    	slowDownPoint = requestedEncoderPos - 18;
    	speedUpPoint = startingEncoderPos + oneFoot;
    }

    // Called repeatedly when this Command is scheduled to run
    /* So, what is this mess? That's a good question. If one carefully reads the if statements, they should get
     * a good idea of when each block will run, so I won't go into that. However, one will notice that the first
     * and third blocks contain "calculatePowerHyperbolic" REFER TO -- LiftSubsytem:177
     * 
     * Well, then. Try getting that out of your head. If the lift is in the sweet spot between speeding up and
     * slowing down, it'll go full speed.
     * 
     * If it goes too far, or hits the limit switch (which better not happen), we call it quits.
     */
    protected void execute() {
    	// TODO check the limit switches FIRST!!!

    double encoderPos = Math.abs(Robot.liftSubsystem.readEncoderInInches());
	    if(encoderPos > requestedEncoderPos || Robot.liftSubsystem.isTopLimitDepressed() == true) {
	    	weAreDoneSenor = true;
	  //  	System.out.println("We're done");
	    } else if((encoderPos <= (speedUpPoint) && encoderPos < slowDownPoint) || Robot.liftSubsystem.isBottomLimitDepressed()){
    		Robot.liftSubsystem.autoMoveElevatorUp(
    				Robot.liftSubsystem.calculatePowerHyperbolic(desiredStartingPower, encoderPos, startingEncoderPos, speedUpPoint, maxPower));

    	//	System.out.println(Robot.liftSubsystem.calculatePowerHyperbolic(desiredStartingPower, encoderPos, startingEncoderPos, speedUpPoint, maxPower));

    	} else if(encoderPos > speedUpPoint && encoderPos < slowDownPoint){
    		Robot.liftSubsystem.setElevatorVelocity(maxPower);
  //  		System.out.println("We're set to maximum overdrive.");
    	} else if(encoderPos >= slowDownPoint) {
    		Robot.liftSubsystem.autoMoveElevatorUp(
    				Robot.liftSubsystem.calculatePowerHyperbolic(desiredEndingPower, encoderPos, requestedEncoderPos, slowDownPoint, maxPower));
    		// System.out.println(Robot.liftSubsystem.calculatePowerHyperbolic(desiredEndingPower, encoderPos, requestedEncoderPos, speedUpPoint, maxPower));

    	}
    	
    }
    /*	As you probably now attest to, programmers should get routinely checked for insanity.
     * 
     * 	The Moral of the Story is if this Command does not work within A LITTLE debugging, abandon it.
     * 	Move on to the PositionP Commands. NOTE: You actually can still use AutoMoveLiftDown instead of AutoPositionPMoveLiftDown.
     * 
     * 	No, really. Move on to AutoPositionPMoveLiftUp: 7
  	*/

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	
    	if(weAreDoneSenor == true) {

    		return true;
    	} else {
    		return false;
    	}
    	
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("Ending AutoMoveLiftUp Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("Interrupting AutoMoveLiftUp Command, encoder inches = {}", Robot.liftSubsystem.readEncoderInInches());
    	
    }
}
