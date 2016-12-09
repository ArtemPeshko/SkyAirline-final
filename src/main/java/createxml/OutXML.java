package createxml;

import airpark.AviaPort;
import interfaces.DrainFuel;
import main.MainRun;

import java.util.HashMap;

public class OutXML extends AviaPort implements DrainFuel {

   public static HashMap<Integer, OutXML> currentjet = new HashMap<>();

    public static String masiv[];


    public String name;
    public int speed;
    public int range;
    public int weight;
    public int fuel;
    public int tank;
    public int number;
    public int passenger;
    public int passenger_seat;
    public int flyCost;

    public OutXML(String name, int number, int  speed, int range, int weight, int fuel, int tank, int passenger, int passenger_seat, int flyCost  ) {
        this.name = name;
        this.number = number;
        this.speed = speed;
        this.range =range;
        this.weight = weight;
        this.fuel = fuel;
        this.tank = tank;
        this.passenger = passenger;
        this.passenger_seat = passenger_seat;
        this.flyCost = this.passenger * ticketcost;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public int getWeight() {
        return weight;
    }

    public int getFuel() {
        return fuel;
    }

    public int getTank() {
        return tank;
    }

    public int getPassenger() {
        return passenger;
    }

    public int getPassenger_seat() {
        return passenger_seat;
    }

    public int getFlyCost() {
        return flyCost;
    }

    public int getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setPassenger_seat(int passenger_seat) {
        this.passenger_seat = passenger_seat;
    }



    public void inPassenger() {
        if ((this.passenger + 10) > this.passenger_seat) this.passenger = this.passenger_seat;
        else this.passenger += 10;
        this.flyCost = this.passenger * ticketcost;
    }

    public void outPassenger() {
        if (this.passenger >= 10) {
            this.passenger -= 10;
        } else this.passenger = 0;
        this.flyCost = this.passenger * ticketcost;
    }

    public void inFuel() {
        if (MainRun.main.money - inBak * MainRun.main.fuelcost > 0) {
            if ((this.fuel + inBak) > this.tank) this.fuel = this.tank;
            else {
                this.fuel += inBak;
                MainRun.main.money -= inBak * MainRun.main.fuelcost;
            }
        }
    }

    public void outFuel() {
        if (this.fuel > 100) {
            this.fuel -= super.outBak();
            MainRun.main.money += 100 * MainRun.main.fuelcost;
        } else this.fuel = 0;

    }

    public String toString() {
        return "aircraft model - " + this.name + "-" + this.number + "! speed - " +
                this.speed + "! range - " + this.range + "! weight - " +
                this.weight + "! fuel - " + this.fuel + "! tank capacity - " + this.tank + "! passenger - " +
                this.passenger + "! passenger seat - " +
                this.passenger_seat + "! fly cost - " ;
    }


}
