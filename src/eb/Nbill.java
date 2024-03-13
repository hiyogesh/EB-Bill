package eb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Nbill {
	private Connection con;	
	//Admin_Login
	boolean login(String u,String p) {
		boolean r=false;
		try {
		String query="select *from adlog where Name='"+u+"'and Password='"+p+"'";
		Statement smt=con.createStatement();
		ResultSet rs=smt.executeQuery(query);
		while(rs.next()) {
			r=true;
		}	
		}catch(SQLException e) {	System.out.println("Error : "+e);	}			
		return r;
	}
	
	//Connectivity
	public Nbill () {	
		String dburl="jdbc:mysql://localhost:3306/ElectricityBill";
		String dbuser="root";
		String dbpass="";		
		try {
		con=DriverManager.getConnection(dburl,dbuser,dbpass);	
		}catch(SQLException e) {	
				System.out.println("Error : "+e);	
		}	
	}
	//Insertion
		public int insert(String Name,String Bill_Date,String Due_Date,double Units,double Amount) {
			int c=0;
			try {
			String query="insert into Bill(Name,Bill_Date,Due_Date,Units,Amount) values(?,?,?,?,?)";	
			PreparedStatement pst=con.prepareStatement(query);
			pst.setString(1, Name);
			pst.setString(2, Bill_Date);
			pst.setString(3, Due_Date);
			pst.setDouble(4, Units);
			pst.setDouble(5, Amount);
			c=pst.executeUpdate();
			}catch(SQLException e) {
				System.out.println("Error : "+e);	
			}	
			return c;
		}
		//Selection
		public void select() {
			try {	
				String query="select *from Bill";	
				PreparedStatement pst=con.prepareStatement(query);
				ResultSet rs=pst.executeQuery();	
				
				while(rs.next()) {
				
				System.out.println(
						rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)
						+"\t"+rs.getString(4)+"\t"+rs.getDouble(5)+"\t"+rs.getDouble(6)
						+"\t"+rs.getDouble(7)+"\t"+rs.getDouble(8)+"\t"+rs.getDouble(9)+"\t"+rs.getString(10));		
				}				
			}catch(SQLException e) {
				System.out.println("Error : "+e);
			}	
		}
		//Updation
		public int updatename(int ID,String Name) {
			int c=0;
			try {
			String query="update Bill set Name=? where ID=?";	
			PreparedStatement pst=con.prepareStatement(query);
			pst.setString(1, Name);
			pst.setInt(2, ID);
			c=pst.executeUpdate();
			}catch(SQLException e) {	System.out.println("Error : "+e);	}	
			return c;
		}
		public int updatebd(int ID,String BD) {
			int c=0;
			try {
			String query="update Bill set Bill_Date=? where ID=?";	
			PreparedStatement pst=con.prepareStatement(query);
			pst.setString(1, BD);
			pst.setInt(2, ID);
			c=pst.executeUpdate();
			}catch(SQLException e) {	System.out.println("Error : "+e);	}	
			return c;
		}
		public int updateDD(int ID,String DD) {
			int c=0;
			try {
			String query="update Bill set Due_Date=? where ID=?";	
			PreparedStatement pst=con.prepareStatement(query);
			pst.setString(1, DD);
			pst.setInt(2, ID);
			c=pst.executeUpdate();
			}catch(SQLException e) {	System.out.println("Error : "+e);	}	
			return c;
		}
		public int updateUnit(int ID,double Units) {
			double balance=0;
			double amt=0;
			double tAmt=0;
			double charge=0;
			int c=0;
			int c1=0;
			try {
				String query1="select Amount from Bill where ID=?";	
				PreparedStatement pst1=con.prepareStatement(query1);
				pst1.setInt(1, ID);
				ResultSet rs=pst1.executeQuery();
				while(rs.next()) {
					balance=rs.getDouble(1);
					System.out.println("Balance :"+balance);
				}
				String query2="update Bill set Units=? where ID=?";	
				PreparedStatement pst2=con.prepareStatement(query2);
				pst2.setDouble(1, Units);
				pst2.setInt(2, ID);
				c1=pst2.executeUpdate();
				if(Units>300) {
					amt=((Units-300)*0.60)+(200*0.50)+(100*0.40);
					if(amt>250) {
						System.out.println("Total Amount :Rs."+amt);
						charge=((amt*15)/100);
						amt=charge+amt;
						System.out.println("Additional Charge 15% :Rs."+charge);
						System.out.println("Total pay Amount :Rs."+amt);
					}else {
						System.out.println("Total pay Amount :Rs."+amt);
					}
				 }
				 else if(Units>100&&Units<=300) {
					 amt=((Units-100)*0.50)+(100*0.40);
					 System.out.println("Total pay Amount :Rs."+amt);
				 }
				 else if(Units>=0&&Units<=100) {
					 amt=(Units*0.40);
					 System.out.println("Total Amount :Rs."+amt);
					 
				 }
				 else{
					 System.out.println("Invalid Units...!!!");
				 }
				tAmt=balance+amt;
				String query="update Bill set Amount=? where ID=?";	
				PreparedStatement pst=con.prepareStatement(query);
				pst.setDouble(1, tAmt);
				pst.setInt(2, ID);
				c=pst.executeUpdate();
				String query3="update Bill set Bal_Amt=?,New_Amt=? where ID=?";	
				PreparedStatement pst3=con.prepareStatement(query3);
				pst3.setDouble(1, balance);
				pst3.setDouble(2, amt);
				pst3.setInt(3, ID);
				c=pst3.executeUpdate();
		
			}catch(SQLException e) {	System.out.println("Error : "+e);	}	
			return c;
		}
		public int updateAmt(int ID,double Amt) {
			int c=0;
			try {
			String query="update Bill set Amount=? where ID=?";	
			PreparedStatement pst=con.prepareStatement(query);
			pst.setDouble(1, Amt);
			pst.setInt(2, ID);
			c=pst.executeUpdate();
			}catch(SQLException e) {	System.out.println("Error : "+e);	}	
			return c;
		}
		//Deletion
				public int delete(int ID) {
					int c=0;
					try {
					String query="delete from Bill where Regno=?";	
					PreparedStatement pst=con.prepareStatement(query);
					pst.setInt(1, ID);
					c=pst.executeUpdate();
					}catch(SQLException e) {
						System.out.println("Error : "+e);	
					}	
					return c;
				}
//USER SIDE
				//Selection
				public void Uselect(int ID) {
					int c=0;
					try {	
						String query="select Name,Due_Date,Units,Charge,Bal_Amt,New_Amt,Amount from Bill where ID=?";	
						PreparedStatement pst=con.prepareStatement(query);
						pst.setInt(1, ID);
						ResultSet rs=pst.executeQuery();
						while(rs.next()) {
							System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3)+"\t"+rs.getDouble(4)
							+"\t"+rs.getDouble(5)+"\t"+rs.getDouble(6)+"\t"+rs.getDouble(7));		
						}				
					}catch(SQLException e) {
						System.out.println("Error : "+e);
					}
					return;
				}
			//Updation
			
				public int Uupdate(int ID,double Pamt) {
					double balance=0;
					int c=0;
					try {
						String query1="select Amount from Bill where ID=?";	
						PreparedStatement pst1=con.prepareStatement(query1);
						pst1.setInt(1, ID);
						ResultSet rs=pst1.executeQuery();
						while(rs.next()) {
							balance=rs.getDouble(1);
						}
						String query2="update Bill set Amount=? where ID=?";	
						PreparedStatement pst2=con.prepareStatement(query2);
						double Amount=balance-Pamt;
						pst2.setDouble(1, Amount);
						pst2.setInt(2, ID);
						c=pst2.executeUpdate();
					}catch(SQLException e) {	System.out.println("Error : "+e);	}	
						return c;
				}
				public int updatePDate(int ID,String PD) {
					int c=0;
					try {
					String query="update Bill set Paying_Date=? where ID=?";	
					PreparedStatement pst=con.prepareStatement(query);
					pst.setString(1, PD);
					pst.setInt(2, ID);
					c=pst.executeUpdate();
					}catch(SQLException e) {	System.out.println("Error : "+e);	}	
					return c;
				}
				boolean DateCheck(int ID,String PD) {
					boolean r=false;
					String DD = null;
					String BD = null;
					try {
						String query1="select Bill_Date,Due_Date from Bill where ID=?";	
						PreparedStatement pst1=con.prepareStatement(query1);
						pst1.setInt(1, ID);
						ResultSet rs=pst1.executeQuery();
						while(rs.next()) {
							BD=rs.getString(1);
							DD=rs.getString(2);
						}
					String query="select *from Bill where Paying_Date BETWEEN '"+BD+"' and '"+DD+"'";
					Statement smt=con.createStatement();
					ResultSet rs2=smt.executeQuery(query);
					while(rs2.next()) {
						r=true;
					}	
					}catch(SQLException e) {	System.out.println("Error : "+e);	}			
					return r;
				}
				boolean IDCheck(int id) {
					boolean r=false;
					try {
					String query="select *from bill where ID="+id+"";
					Statement smt=con.createStatement();
					ResultSet rs=smt.executeQuery(query);
					while(rs.next()) {
						r=true;
					}	
					}catch(SQLException e) {	System.out.println("Error : "+e);	}			
					return r;
				}
				//Check Paying Date
				boolean CheckPD(int ID,String PD) {
					boolean r=false;
					String BD = null;
					try {
						String query="select Bill_Date from Bill where ID=?";	
						PreparedStatement pst=con.prepareStatement(query);
						pst.setInt(1, ID);
						ResultSet rs=pst.executeQuery();
						while(rs.next()) {
							BD=rs.getString(1);
						}
					String query1="select *from Bill where Paying_Date BETWEEN '0000-00-00' and '"+BD+"'";
					Statement smt=con.createStatement();
					ResultSet rs1=smt.executeQuery(query1);
					while(rs1.next()) {
						r=true;
					}	
					}catch(SQLException e) {	System.out.println("Error : "+e);	}			
					return r;
				}
				//Update Amount
				public int UupdateAmt(int ID,double Pamt) {
					double Tbalance=0;
					double balance=0;
					double Charge=0;
					int c=0;
					try {
						String query1="select Amount from Bill where ID=?";	
						PreparedStatement pst1=con.prepareStatement(query1);
						pst1.setInt(1, ID);
						ResultSet rs1=pst1.executeQuery();
						while(rs1.next()) {
							balance=rs1.getDouble(1);
						}
						Charge=balance*0.15;
						System.out.println(balance);
						System.out.println(Charge);
						Tbalance=balance+Charge;
						String query2="update Bill set Charge=? where ID=?";	
						PreparedStatement pst2=con.prepareStatement(query2);
						pst2.setDouble(1, Charge);
						pst2.setInt(2, ID);
						c=pst2.executeUpdate();
						String query3="update Bill set Amount=? where ID=?";	
						PreparedStatement pst3=con.prepareStatement(query3);
						double Amount=Tbalance-Pamt;
						pst3.setDouble(1, Amount);
						pst3.setInt(2, ID);
						c=pst3.executeUpdate();
					}catch(SQLException e) {	System.out.println("Error : "+e);	}	
						return c;
				}
}
