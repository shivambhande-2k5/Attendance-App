package com.example.attendanceapp;

public class Student {

    private int ID;
    private String firstName ;
    private String lastName;
    private String email;
    private String phoneNumber;

    public int getID (){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public String getfirstName(){           // FIRSTNAME GETTER
        return firstName;
    }

    public void setFirstName(String firstName){      // FIRSTNAME SETTER
        this.firstName = firstName;
    }

    public String getlastName(){           // LASTNAME GETTER
        return lastName;
    }

    public void setlastName(String lastName){      // LASTNAME SETTER
        this.lastName = lastName ;

    }

    public String getEmail(){          // EMAIL GETTER
        return email;

    }

    public void setEmail(String email){    // EMAIL SETTER
        this.email = email;
    }

    public String getPhoneNumber(){          // PHONE_NUMBER GETTER
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){     // PHONE_NUMBER SETTER
        this.phoneNumber = phoneNumber;
    }



}
