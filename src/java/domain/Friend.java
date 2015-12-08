// Friend.java

//  Adapted from the Module 4 Example

/**
 *
 * @author Alan Johnson
 */
package domain;

public class Friend implements java.io.Serializable {
    
   int objectID;
   String firstName;
   String lastName;
   String phone;
   
//The default constructor is public to be accessible from JSP View pages. 
   
   public Friend() {this(-1,"","","");}
   //The full constructor is package-private to prevent misuse. 
   //The RegisterServer method createStudent should be used to create 
   //a new Student object.
   
   Friend(int oID, String fn, String ln, String p){
      objectID=oID;
      firstName = fn;
      lastName = ln;
      phone = p;
   }
   
   public void setFirstName(String n) {
       if (n != null && !n.equals(""))
           firstName = n;
   }
   public String getFirstName() {return firstName;}
   public void setLastName(String n) {
       if (n != null && !n.equals(""))
           lastName = n;
   }
   public String getLastName() {return lastName;}
   public void setPhone (String p) {
       if (p.length() == 10)
       phone = p;
   }
   public String getPhone() {return phone;}
   public String getPhoneFormatted() {return String.format("(%s) %s- %s", phone.substring(0, 3), phone.substring(3,6), phone.substring(6,10)); }
   public void setObjectID(int id) {objectID=id;}
   public int getObjectID() {return objectID;}
}