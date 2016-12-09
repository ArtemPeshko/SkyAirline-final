package airpark;

import interfaces.Cache;
import interfaces.FillFuel;
import main.MainRun;

import java.util.ArrayList;

public class AviaPort extends Aircraft implements FillFuel, Cache, java.io.Serializable {


    public static ArrayList<AviaPort> all = new ArrayList<>();
    public String name;
    public int speed;
    public int range;
    public int weight;
    public int fuel;
    public int tank;
    public int number;
    public int passenger;
    public int passengerSeat;
    public int flyCost;


    public AviaPort() {
        this.name = super.name();
        this.speed = super.speed();
        this.range = super.range();
        this.weight = super.weight();
        this.fuel = super.fuel();
        this.number = super.number();
        this.tank = super.tank();
        this.passenger = super.passenger();
        this.passengerSeat = super.passengerSeat();
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

    public int getPassengerSeat() {
        return passengerSeat;
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

    public void inPassenger() {
        if ((this.passenger + 10) > this.passengerSeat) this.passenger = this.passengerSeat;
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
        if (MainRun.money - inBak * MainRun.fuelcost > 0) {
            if ((this.fuel + inBak) > this.tank) this.fuel = this.tank;
            else {
                this.fuel += inBak;
                MainRun.money -= inBak * MainRun.fuelcost;
            }
        }


    }


    public void outFuel() {

        if (this.fuel > 100) {
            this.fuel -= super.outBak();
            MainRun.money += 100 * MainRun.fuelcost;
        } else this.fuel = 0;

    }


    public String toString() {
        return "aircraft model - " + this.name + "-" + this.number + "! speed - " +
                this.speed + "! range - " + this.range + "! weight - " +
                this.weight + "! fuel - " + this.fuel + "! tank capacity - " + this.tank + "! passenger - " +
                this.passenger + "! passenger seat - " +
                this.passengerSeat + "! fly cost - " + this.flyCost;
    }


    public static int settings(int setting, int aa) {
        if (setting == 1)
            for (AviaPort a : all) {
                if (a.getNumber() == aa) {
                    a.inFuel();
                }
            }
        return setting;
    }

    public static int settingss(int setting, int aa) {
        if (setting == 2)
            for (AviaPort a : all) {
                if (a.getNumber() == aa) {
                    a.outFuel();
                }
            }
        return setting;
    }


}

