package eb;
import java.util.Scanner;
public class AdAndCus{
	 static Nbill db=new Nbill();
 public static void Admin() {
	 Scanner sc=new Scanner(System.in);
		System.out.print("Enter the User Name : ");
		String u=sc.next();
		System.out.print("Enter the Password : ");
		String p=sc.next();
		if(db.login(u, p)) {
			while(true) {
			System.out.println("Welcome");
			System.out.print("1.Add User Details\n2.View User Details\n");
			System.out.print("3.Edit User Details\n4.Remove User Details");
			System.out.println("\n5.Exit");
			System.out.print("Enter the Choice : ");
			int ch=sc.nextInt();
			if(ch==1) {
				double tpay = 0;
				 double charge;
				System.out.print("Enter Name :");
				String Name=sc.next();
				System.out.print("Enter Bill Date :");
				String BDate=sc.next();
				System.out.print("Enter Due Date :");
				String DDate=sc.next();
				System.out.print("Enter Units :");
				double units=sc.nextDouble();
				if(units>300) {
					tpay=((units-300)*0.60)+(200*0.50)+(100*0.40);
					if(tpay>250) {
						System.out.println("Total Amount :Rs."+tpay);
						charge=((tpay*15)/100);
						tpay=charge+tpay;
						System.out.println("Additional Charge 15% :Rs."+charge);
						System.out.println("Total pay Amount :Rs."+tpay);
					}else {
						System.out.println("Total pay Amount :Rs."+tpay);
					}
				 }
				 else if(units>100&&units<=300) {
					 tpay=((units-100)*0.50)+(100*0.40);
					 System.out.println("Total pay Amount :Rs."+tpay);
				 }
				 else if(units>=0&&units<=100) {
					 tpay=(units*0.40);
					 System.out.println("Total Amount :Rs."+tpay);
					 
				 }
				 else{
					 System.out.println("Invalid Units...!!!");
				 }
				int res=db.insert(Name,BDate,DDate,units,tpay);
				System.out.println((res>0)?"Inserted data":"Not inserted Data");
				}
			else if(ch==2) {
				System.out.println("ID\tName\tB.Date\t\tD.Date\t\tUnits\tBal_Amt\tNew_Amt\tCharge\tAmount\tPaY_Date");
				db.select();
				System.out.println("---------------------------<<-End of the Records->>------------------------------");
			}
			else if(ch==3) {
				System.out.println("1.Name Change\n2.Bill Date Change\n3.Due Date Change\n4.Units Change\n5.Amount Change\n6.Exit");
				System.out.print("Enter Your Choice :");
				int cho=sc.nextInt();
				if(cho==1) {
					System.out.print("Enter the ID : ");
					int reg=sc.nextInt();
					if(db.IDCheck(reg)) {
						System.out.print("Enter the Name : ");
						String n=sc.next();
						int res=db.updatename(reg,n);
						System.out.println((res>0)?"Updated data":"Not Updated Data");
					}
					else {
						System.out.println("Invalid EB ID....Give Valid ID....!!!");
						Admin();
					}
				}
				else if(cho==2) {
					System.out.print("Enter the ID : ");
					int reg=sc.nextInt();
					if(db.IDCheck(reg)) {
						System.out.print("Enter the Bill Date : ");
						String n=sc.next();
						int res=db.updatebd(reg,n);
						System.out.println((res>0)?"Updated data":"Not Updated Data");
					}
					else {
						System.out.println("Invalid EB ID....Give Valid ID....!!!");
						Admin();
					}
				}
				else if(cho==3) {
					System.out.print("Enter the ID : ");
					int reg=sc.nextInt();
					if(db.IDCheck(reg)) {
						System.out.print("Enter the Due Date : ");
						String n=sc.next();
						int res=db.updateDD(reg,n);
						System.out.println((res>0)?"Updated data":"Not Updated Data");
					}
					else {
						System.out.println("Invalid EB ID....Give Valid ID....!!!");
						Admin();
					}
				}
				else if(cho==4) {
					System.out.print("Enter the ID: ");
					int reg=sc.nextInt();
					if(db.IDCheck(reg)) {
						System.out.print("Enter the Units : ");
						double n=sc.nextDouble();
						int res=db.updateUnit(reg,n);
						System.out.println((res>0)?"Updated data":"Not Updated Data");
					}
					else {
						System.out.println("Invalid EB ID....Give Valid ID....!!!");
						Admin();
					}
				}
				else if(cho==5) {
					System.out.print("Enter the ID: ");
					int reg=sc.nextInt();
					if(db.IDCheck(reg)) {
						System.out.print("Enter the Amount : ");
						double n=sc.nextDouble();
						int res=db.updateAmt(reg,n);
						System.out.println((res>0)?"Updated data":"Not Updated Data");
					}
					else {
						System.out.println("Invalid EB ID....Give Valid ID....!!!");
						Admin();
					}
				}
				else if(cho==6) {
					
				}
				else {
					System.out.println("Invalid Choice...");
				}
			}
			else if(ch==4) {
				System.out.print("Enter the ID : ");
				int reg=sc.nextInt();
				if(db.IDCheck(reg)) {
					int res=db.delete(reg);
					System.out.println((res>0)?"Deleted data":"Not Deleted Data");
				}
				else {
					System.out.println("Invalid EB ID....Give Valid ID....!!!");
					Admin();
				}
			}
			else if(ch==5) {
				System.out.println("Exiting...!!!");
				break;
			}
			else {
				System.out.println("Invalid Choice");
			}
			System.out.println("----------------------------------------");
			}
			}else {
			System.out.println("You are not a Admin...");
		}
		sc.close();
 }
 //USER SIDE
 public static void customer() {
		Scanner sc=new Scanner(System.in);
		System.out.println("1.View Details\n2.Bill Payment\n3.Exit");
		System.out.print("Enter your choice :");
		int cho=sc.nextInt();
		if(cho==1) {
			System.out.print("Enter Your EB ID :");
			int id=sc.nextInt();
			if(db.IDCheck(id)) {
				System.out.println("Name\tDue_Date\tUnits\tCharge\tBal_Amt\tNew_Amt\tTot_Amount");
				db.Uselect(id);
				System.out.println("---------------------------------------------------------");
				customer();
			}else {
				System.out.println("Invalid EB ID....Give Valid ID....!!!");
				customer();
			}
		}
		else if(cho==2) {
			System.out.print("Enter Your EB ID :");
			int id=sc.nextInt();
			if(db.IDCheck(id)) {
				System.out.println("Enter paying Date :");
				String PD=sc.next();
				db.updatePDate(id, PD);
				if(db.DateCheck(id,PD)) {
					System.out.print("Enter Pay Amount :");
					double Pamt=sc.nextDouble();
					db.Uupdate(id,Pamt);
					customer();
				}else if(db.CheckPD(id,PD)){
				System.out.println("Check in Your EB Office...");
				}else {
				System.out.print("Enter Pay Amount :");
				double Pamt=sc.nextDouble();
				db.updateAmt(id,Pamt);
				customer();
			}
			}else {
				System.out.println("Invalid EB ID....Give Valid ID....!!!");
				customer();
			}
			
		}
		else if(cho==3) {
			System.out.println("Exiting...!!");
		}
		else {
			System.out.println("Invalid Choice...");
			customer();
		}	
	sc.close();
 }
		
 public static void main() {
	 try {
	 Scanner sc=new Scanner(System.in);
	 System.out.println("1.Admin Login\n2.Custimer Login");
	 System.out.print("Enter Your Choice :");
	 int v=sc.nextInt();
	 if(v==1) {
		 Admin();
	 }
	 else if(v==2) {
		 customer();
	 }
	 else {
		 System.out.println("Inavalid choice");
	 }
	 }catch(Exception e) {
		 System.out.println("Error :"+e);
	 }
 }
 public static void main(String[]args) {
	 main();
 }
}