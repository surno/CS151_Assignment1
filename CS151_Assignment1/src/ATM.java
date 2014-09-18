import java.util.Scanner;


/**
  ATM Class that interacts with the user and the bank computer
  ATM class for CS 151 Assignment #1  
  @author Antonio Ontiveros
  @version 1.0 9/10/2014 
 */
public class ATM
{
   private static final int INCORRECT_ATTEMPTS = 3;
   
   private int ATMNumber;
	private double withdrawLimit;
   private double transactionLimit;
	private double cashOnATM;
	private double minimumCash;
	private String bankName;
	private String ATMName;
	private CashCard currentCustomer;
	private Bank supportedBank;
	

	public ATM(String bankName, int ATMNumber, Bank newBank, int transactionLimit
	      , int withdrawLimit, int minimumCash, int cashOnATM)
	{
		this.ATMNumber = ATMNumber;
		this.bankName = bankName;
		this.transactionLimit = transactionLimit;
		this.cashOnATM = cashOnATM;
		this.minimumCash = minimumCash;
		this.withdrawLimit = withdrawLimit;
		
		supportedBank = newBank;
		ATMName = "ATM" + bankName + "_" + ATMNumber;
	}
	public void run()
	{
	   String progress = "yes";
	   while(!progress.equals("quit"))
	   {
	      if(checkCard())
	         if(checkPassword())
	            progress = completeDeposit();         
	   }
	}
	
	private  String completeDeposit()
   {
      Scanner in = new Scanner(System.in);
      String accountInput = in.next();
      while(!accountInput.equals("Checking") && !accountInput.equals("Savings"))
      {
         System.out.println("Sorry, please enter Savings or Checking: ");
         accountInput = in.next();
      }
      if(accountInput.equals("Checking"))
      {
         System.out.println("Current balance: " + 
      supportedBank.getCheckingBalance(currentCustomer));
         boolean successWithdraw = false;
         while(!successWithdraw)
         {
            System.out.println(
                   "Please enter an ammount you like to withdraw: ");
            double withdrawAmount = in.nextDouble();
            if(withdrawAmount > withdrawLimit || withdrawAmount > transactionLimit)
            {
               System.out.println("Please enter an ammount smaller than "
                     + withdrawLimit);
            }
            else if(withdrawAmount > cashOnATM || cashOnATM == 0)
            {
               System.out.println("Sorry, the ATM only has " + cashOnATM);
            }
            else if(withdrawAmount > supportedBank.getCheckingBalance(currentCustomer))
            {
               System.out.println("Isufficient funds, please try a different"
                     + " a different ammount.");
            }
            else
            {
               supportedBank.withdrawChecking(withdrawAmount, currentCustomer);
               
            }
            
            
         }
      }
         else if(accountInput.equals("Savings"))
         {
            System.out.println("Current balance: " + 
         supportedBank.getSavingsBalance(currentCustomer));
            boolean successWithdraw = false;
            while(!successWithdraw)
            {
               System.out.println(
                      "Please enter an ammount you like to withdraw: ");
               double withdrawAmount = in.nextDouble();
               if(withdrawAmount > withdrawLimit || withdrawAmount > transactionLimit)
               {
                  System.out.println("Please enter an ammount smaller than "
                        + withdrawLimit);
               }
               else if(withdrawAmount > cashOnATM || cashOnATM == 0)
               {
                  System.out.println("Sorry, the ATM only has " + cashOnATM);
               }
               else if(withdrawAmount > supportedBank.getSavingsBalance(currentCustomer))
               {
                  System.out.println("Isufficient funds, please try a different"
                        + " a different ammount.");
               }
               else
               {
                  supportedBank.withdrawSavings(withdrawAmount, currentCustomer);
               }
               
               
            }
         
      }
      
      return "hello";
   }
   private boolean checkPassword()
   {
	   Scanner in = new Scanner(System.in);
	   int incorrectCounter = 0;
	   if(supportedBank.verifyUserPassword(in.nextLine(), currentCustomer))
	   {
	      System.out.println("Welcome! Which account would you like to "
	            + "access?:");
	      return true;
	   }
	   else
	   {
	      incorrectCounter++;
	      while (incorrectCounter < INCORRECT_ATTEMPTS)
	      {
	         System.out.println("Incorrect Password, Please try again (" +
	              (INCORRECT_ATTEMPTS - incorrectCounter) +" attempts left): ");
	         
	         if(supportedBank.verifyUserPassword(in.nextLine(), currentCustomer))
	         {
	            System.out.println("Welcome! Which account would you like to "
	                  + "access?");
	            return true;
	         }
	      }
	      return false;
	   }

      
   }
   private boolean checkCard()
   {
      System.out.println("Please enter your card information: ");
      Scanner in = new Scanner(System.in);
      String inputBankID = in.next();
      int inputCardID = in.nextInt();
      currentCustomer = new CashCard(inputBankID, inputCardID);
      if(inputBankID.equals(supportedBank.getBankID()))
      {
         if(supportedBank.verifyExpiration(currentCustomer))
         {
            System.out.println("This card is accepted, please enter your"
                  + " password: ");
            return true;
         }
         else
         {
            System.out.println("Sorry your card is expired.");
            return false;
         }
      }
      else
      {
         System.out.println("Sorry, this ATM doesn't support your Bank.");
         return false;
      }
      
      
   }
   public double getWithdrawLimit()
   {
      return withdrawLimit;
   }
   public void setWithdrawLimit(float withdrawLimit)
   {
      this.withdrawLimit = withdrawLimit;
   }
   public double getTransactionLimit()
   {
      return transactionLimit;
   }
   public void setTransactionLimit(float transactionLimit)
   {
      this.transactionLimit = transactionLimit;
   }
   public double getCashOnATM()
   {
      return cashOnATM;
   }
   public void setCashOnATM(float cashOnATM)
   {
      this.cashOnATM = cashOnATM;
   }
   

}
