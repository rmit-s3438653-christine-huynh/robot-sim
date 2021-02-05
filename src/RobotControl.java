 
class RobotControl
{
	private Robot r; 
//--My Variables.---------------------------------------------------------------------/
	private int barMax = 0;		// Maximum value for barHeights.
	private int sourceHt = 0; 	// Total height for source Block(s).
	private int targetHt = 0; 	// Total height for target Block(s).
	private int aBlock = 0; 	// Individual Block height.
	private int armHt = 2;		// Arm height variable, keep track.
	// Part B & C.
	private int varHt = 0; 		// Height variable for sourceHt or barMax.
	private int offset = 1;		// Essential formatting to correctly run code,
	// Part D.
	private int tempHt = 0;		// Height for Temporary target, storage for Block.
	private int maxSourceHt = 0;// Max value from targetHt, barMax, tempHt, sourceHt.
	private int maxTempHt = 0;	// Max value from targetHt, barMax, tempHt.
	private int maxBlockHt = 0;	// Height variable for maxTempHt or maxSoureceHt.
	private int holdLength = 0; // Temporary hold the value of string.length.
	private int armWidth = 1;	// Arm width variable, keep track.
	private int indexOfSource = 0;		// Index for Source array.
	private int indexOfTemp = 0;		// Index for Temp array.
	private String sourceString = "";	// String array for Source.
	private String tempString = "";		// String array for Temp.
	private String requiredString = "";	// String array for Required.
	private String requiredBlock = "";	// String array for Block that is Required.
	private String charString = "";		// Temporary storage for Block being moved.

	public RobotControl(Robot r)
	{
		this.r = r; 
	}

//-Initializing variables to start Robot.-------------------------------------method0-/
	public void prepareRobot(int blockHeights[], int barHeights[])
	{
		// To find maximum value for BarHeight and save as barMax.
		for (int barHt = 0; barHt < barHeights.length; barHt++)
		{
			if (barHeights[barHt] > barMax)
			{
				barMax = barHeights[barHt];
			}
		}
		// Adding the total blockHeights at Source and saving them as sourceHt.
		for (int s = 0; s < blockHeights.length; s++) // 's' is the Counter for the array.
		{
			sourceHt += blockHeights[s];
		}
		// Reading 1st individual block and saving the index.
		aBlock = blockHeights.length - 1; // index 2, -1 refers to logical array. (0,1,2,3)
	}
//-Finding maximum values for arm Height.-----------------------------------method1.A-/
// Using barMax, sourceHt.
	public void findMaximum(int barHeights[])
	{
		// If barMax is greater than sourceHt. 
		// Save barMax as ArmHeight variable as varHt. 
		if (barMax > sourceHt)
		{
			varHt = barMax;
		}
		// Or if  sourceHt is greater than barMax. 
		// Save sourceHt as ArmHeight variable as varHt.
		else // (tempHt >= sourceHt)
		{
			varHt = sourceHt;
		}
	}
//-Finding maximum values for arm Height.-----------------------------------method1.B-/
// For targetHt, barMax, tempHt, sourceHt.
	public void findMaxAll()
	{
		// Maximum value from targetHt, barMax, tempHt. 
		maxTempHt = Math.max(targetHt, Math.max(barMax, tempHt));
		// Maximum value from targetHt, barMax, tempHt, sourceHt.
		maxSourceHt = Math.max(targetHt, Math.max(barMax, 
				Math.max(tempHt, sourceHt)));

		// If maxTempHt is greater than maxSourceHt. 
		// Save maxTempHt as ArmHeight variable as maxBlockHt. 
		if (maxTempHt > maxSourceHt)
		{
			maxBlockHt = maxTempHt;
		}
		// Or if  maxSourceHt is greater than maxTempHt. 
		// Save maxSourceHt as ArmHeight variable as maxBlockHt.
		else // (maxSourceHt > maxTempHt)
		{
			maxBlockHt = maxSourceHt;
		}
	}
//-Moving robot arm Height to the max value to clear targetHt, varHt.-------method2.A-/
// Needs findMaximum(), where varHt = barMax, sourceHt, bacon.
	public void initialHeightVar() 
	{
		// If varHt(sourceHt or barMax) is greater than or equal to targetHt.
		// Arm Height goes up or down depending on the heights of sourceHt or barMax.
		if (varHt >= targetHt)
		{
			while (armHt < varHt + offset)
			{
				r.up();
				armHt += 1;
			}
			while (armHt > varHt + offset)
			{
				r.down();
				armHt -= 1;
			}
		}
		// Or when targetHt is greater than varHt(sourceHt or barMax).
		// Arm Height goes up or down depending on the heights of targetHt.
		else if (targetHt > varHt)
		{
			while (armHt < targetHt + offset)		
			{
				r.up();
				armHt += 1;
			}
			while (armHt > targetHt + offset)
			{
				r.down();
				armHt -= 1;
			}  
		}
	}
//-Moving robot arm Height to the max value to clear maxBlockHt.------------method2.B-/
// Needs findMaxAll(), uses armHt, targetHt, maxBlockHt, rbtOffset.
	public void initialBlockHtVar() 
	{
		// If targetHt is greater than maxBlockHt.
		// Arm Height goes up or down depending on the heights of targetHt.
		if (targetHt > maxBlockHt + offset)
		{
			while (armHt < targetHt)
			{
				r.up();
				armHt += 1;
			}
			while (armHt > targetHt)
			{
				r.down();
				armHt -= 1;
			}
		}
		// Or else if targetHt is less than maxTempHeight.
		// Arm goes up or down depending on the heights max Block Height.
		else //(targetHt < maxTemp + bacon)
		{
			while (armHt < maxBlockHt + offset)
			{
				r.up();
				armHt += 1;
			}
			while (armHt > maxBlockHt + offset)
			{
				r.down();
				armHt -= 1;
			}				
		}
	}
//-Moving robot arm Width to right, towards Block stored in Source.---------method3.A-/
	public void armExtendSource()
	{
		// Arm extends to right +9 towards Source.
		if (armWidth == 1)
		{
			for (int w = 0; w < 9; w++)
			{
				r.extend(); 
				armWidth++;
			}
		}
		// Arm width extends by +1 from Temp to Source.
		else
		{
			r.extend(); //(+ 1)
			armWidth++;
		}
	}
//-Moving robot arm Width to right, towards Block stored in Temp.-----------method3.B-/
	public void armExtendTemp()
	{
		// Arm extends to right +8 towards Temp.
		if (armWidth == 1)
		{
			for (int w = 0; w < 8; w++)
			{
				r.extend(); 
				armWidth++;
			}
		}
		// Arm width contracts by +1 from Source to Temp.
		else
		{
			r.contract(); //(+ 1)
			armWidth--;
		}
	}
//-Picking Block from Source/Temp.--------------------------------------------method4A/
	public void pickBlock()
	{
		// If the ArmPicker Height = sourceHt, just pick.
		if (armHt >= sourceHt + 1)
		{
			if (armHt == sourceHt + 1)
			{
				r.pick();
			}
			// Or else the Arm picker needs to lower to reach Block.
			else // sourceHt > armHt
			{
				// Arm picker increases in depth (down).
				for (int d = 0; d < armHt - sourceHt - offset; d++)  
				{
					r.lower(); 
				}
				r.pick();
				// Arm picker retracts in depth (up).
				for (int d = 0; d < armHt - sourceHt - offset; d++)
				{
					r.raise(); 
				}
			}
		}
	}
//-Picking Block from Temp.---------------------------------------------------method4B/
	public void pickBlockTemp()
	{
		// If the ArmPicker Height = sourceHt, just pick.
		if (armHt >= tempHt + 1)
		{
			if (armHt == tempHt + 1)
			{
				r.pick();
			}
			// Or else the Arm picker needs to lower to reach Block.
			else // sourceHt > armHt
			{
				// Arm picker increases in depth (down).
				for (int d = 0; d < armHt - tempHt - offset; d++)  
				{
					r.lower(); 
				}
				r.pick();
				// Arm picker retracts in depth (up).
				for (int d = 0; d < armHt - tempHt - offset; d++)
				{
					r.raise(); 
				}
			}
		}
	}
//-Moving robot arm Width to left, from Source.-----------------------------method5.A-/
	public void armContractSource()
	{
		// Arm contracts to left +9 from Source to Target. 
		for (int w = 0; w < 9; w++)
		{
			r.contract(); 
			armWidth--;
		}
	}
//-Moving robot arm Width to left, from Temp.-------------------------------method5.B-/
	public void armContractTemp()
	{
		// Arm contracts to left +8 from Temp to Target.
		for (int w = 0; w < 8; w++)
		{
			r.contract(); 
			armWidth--;
		}
	}
//-ArmHeight variable for returning Block to Target.--------------------------method6-/
	public void returnHeightVar (int blockHeights[])
	{
		// If targetHt is greater than barMax, 
		// Run using targetHt as ArmHeight variable.	
		if (targetHt >= barMax)
		{
			for (int h = armHt; h < targetHt + blockHeights[aBlock] + 1; h++)
			{
				r.up();
				armHt += 1;
			}
		}
		// Or else if barMax > targetHt,
		// Run using barMax as ArmHeight variable.
		else
		{
			for (int h = armHt; h < barMax + blockHeights[aBlock] + 1; h++)
			{
				r.up(); // Arm Height increases (up).
				armHt += 1;
			}
		}
	}
//-Dropping Block at Target--------------------------------------------------method7A-/
	public void dropBlock(int blockHeights[])
	{
		// Extending Arm picker down to drop block.
		for (int d = 0; d < (armHt - 1) - blockHeights[aBlock] - targetHt; d++) 
		{
			r.lower();
		}
		r.drop();
		// Retracting Arm picker back to initial position.
		for (int d = 0; d < (armHt - 1) - blockHeights[aBlock] - targetHt; d++) 
		{
			r.raise();
		}
	}
//-Dropping Block at Target--------------------------------------------------method7B-/
	public void dropBlockTarget(int blockHeight)
	{
		// Extending Arm picker down to drop block.
		for (int d = 0; d < (armHt - offset) - blockHeight - targetHt; d++) 
		{
			r.lower(); // Arm picker increases in depth (down).
		}
		r.drop();
		// Retracting Arm picker back to initial position.
		for (int d = 0; d < (armHt - offset) - blockHeight - targetHt; d++) 
		{
			r.raise(); // Arm picker retracts in depth (up).
		}
	}
//-Dropping Block at Temp----------------------------------------------------method7C-/
	public void dropBlockTemp(int blockHeight)
	{
		// Extending Arm picker down to drop block.
		for (int d = 0; d < (armHt - offset) - blockHeight - tempHt; d++) 
		{
			r.lower();
		}
		r.drop();
		// Retracting Arm picker back to initial position.
		for (int d = 0; d < (armHt - offset) - blockHeight - tempHt; d++) 
		{
			r.raise();
		}
	}
//-Dropping Block at Source--------------------------------------------------method7C-/		
	public void dropBlockSource(int blockHeight)
	{
		// Extending Arm picker down to drop block.
		for (int d = 0; d < (armHt - offset) - blockHeight - sourceHt; d++) 
		{
			r.lower();
		}
		r.drop();
		// Retracting Arm picker back to initial position.
		for (int d = 0; d < (armHt - offset) - blockHeight - sourceHt; d++) 
		{
			r.raise();
		}
	}

//-Part A,B,C---------------------------------------------------------------methodABC-/
// Robot Arm can pick up Blocks at Source and return them to Target,
// Where barHeights and BlockHeights vary, and can be set by user input.
	void partABC(int barHeights[], int blockHeights[], 
			int required[], boolean ordered)
	{
		prepareRobot(blockHeights ,barHeights);
		// Start Loop part ABC.
		for (int j : blockHeights)
		{
			// Go to Source and pick Block.
			findMaximum(barHeights);
			initialHeightVar();
			armExtendSource();
			pickBlock();
			// Bring Block to Target
			returnHeightVar(blockHeights);
			armContractSource();
			dropBlock(blockHeights);

			// Update the values for sourceHt.
			sourceHt -= blockHeights[aBlock];
			// Update the values for targetHt.
			targetHt += blockHeights[aBlock];
			// Going to next Block array.
			aBlock -= 1;   
		} //Loop part ABC end.
	}
//-Part D---------------------------------------------------------------------methodD-/
	void partD(int barHeights[], int blockHeights[], 
			int required[], boolean ordered)
	{
		// Setting up part D initial variables.
		prepareRobot(blockHeights,barHeights);
		findMaxAll();

		// Setting Arm to go to set Height to avoid collisions.
		for (int z = 0; z < 12; z++ )
		{
			r.up();
			armHt += 1;
		}

		// Creating a String to represent the Blocks in Source, and saving it in sourceString.
		for (int v : blockHeights) 
		{
			sourceString += v;
		}
		// Creating a String to represent the Blocks in required, and saving it in requiredString.
		for (int v : required) 
		{
			requiredString += v;
		}
		// Loop: Part D.
		for (int y : blockHeights)
		{
			// Find the value of the 1st character in requiredString and saving it as requiredBlock.
			requiredBlock = requiredString.substring(0, 1); 

			// Remove the requiredBlock from the String, leaving the rest of requiredString.
			requiredString = requiredString.substring(1);

			// Find required Block in Temp and show index position, where -1 = FALSE.
			indexOfTemp = tempString.lastIndexOf(requiredBlock); 

			// Find required Block in Source and show index position, where -1 = FALSE.
			indexOfSource = sourceString.lastIndexOf(requiredBlock);

            // Using indexOfTemp to sort thru Blocks stored in Temp, to find requiredBlock.
			// Then retrieve and place in Target.
			if (indexOfTemp > -1) // Where -1 = FALSE.
			{
				holdLength = tempString.length();
				// Moving Blocks from Temp to Source, leaving requiredBlock left to pick.
				for (int i = 0; i < holdLength - (indexOfTemp + 1); i++)
				{
					// Robot Arm extends to Temp, picks Block and place in Source.
					armExtendTemp();
					pickBlockTemp();
					r.extend(); 
					armWidth++;

					// Removes last char(String) of tempString, and add to sourceString.
					charString = tempString.substring(tempString.length() - 1);
					tempString = tempString.substring(0, tempString.length()-1);
					dropBlockSource(Integer.parseInt(charString));
					sourceString += charString;

					// Update sourceHt, tempHt.
					tempHt -= Integer.parseInt(charString);
					sourceHt += Integer.parseInt(charString);
				}
				
				// Pick Block required from Temp and retrieve to place in Target.
				armExtendTemp();
				pickBlockTemp();
				armContractTemp();

				// Updating variables, Removing last Block from tempString
				// Drops Block in Target.
				charString = tempString.substring(tempString.length() - 1);
				dropBlockTarget(Integer.parseInt(charString));
				tempString = tempString.substring(0, tempString.length() - 1);

				//update tempHt, targetHt;
				tempHt -= Integer.parseInt(requiredBlock);
				targetHt += Integer.parseInt(requiredBlock);
			}

			// Using indexOfSource to sort thru Blocks stored in Source, to find requiredBlock.
			// Then retrieve and place in Target.
	 		else if (indexOfSource > -1) // Where -1 = FALSE.
			{
				holdLength = sourceString.length();
				// Moving Blocks from Source to Temp, leaving requiredBlock left to pick.
				for (int i = 0; i < holdLength - (indexOfSource + 1); i++)
				{
					// Robot Arm contracts to Source, picks Block and place in Temp.
					armExtendSource();
					pickBlock();
					r.contract();
					armWidth--;

					// Removes last char(String) of sourceString, and add to tempString.
					charString = sourceString.substring(sourceString.length() - 1);
					dropBlockTemp(Integer.parseInt(charString));
					sourceString = sourceString.substring(0, sourceString.length() - 1); // removes substring at index(1).

					tempString += charString ; // 

					// Update sourceHt, tempHt.		  
					sourceHt -= Integer.parseInt(charString);
					tempHt += Integer.parseInt(charString);
				}

				// Pick Block required and retrieve to place in Target.
				armExtendSource();
				pickBlock();
				armContractSource();

				// Removes last char(String) of sourceString.
				charString = sourceString.substring(sourceString.length() - 1);
				sourceString = sourceString.substring(0, sourceString.length() - 1); // removes substring at index(1).

				// Update sourceHt, tempHt.
				dropBlockTarget(Integer.parseInt(charString));
				sourceHt -= Integer.parseInt(charString);
				targetHt += Integer.parseInt(charString);
			}
		}
	}
//-START------------------------------------------------------------------------------/
	public void control(int barHeights[], int blockHeights[], 
			int required[], boolean ordered)
	{
		// Change to True to run Part D. Else change to False to run part ABC.
		boolean runD = false;
		
		if(runD == true)
		{
			partD(barHeights, blockHeights, required, ordered);
		}
		else
		{
			partABC(barHeights, blockHeights, required, ordered);
		}
	} 
}

