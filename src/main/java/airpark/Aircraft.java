package airpark;


import interfaces.DrainFuel;

public class Aircraft extends SkyAirCorp implements DrainFuel {


    String[] Air = {"Boing", "Colt", "AirBus", "Carpet-plane", "Humpbacked-Horse", "Stupa-Baba-Yaga", "Geese-swans", "Hogwarts-Express", "The-core-of-BaronMunchausen"};

    @Override
    String name() {

        String name = Air[(int) (Math.random() * Air.length - 1)];
        return name;
    }

    @Override
    int speed() {
        int speed = (1000 + (int) (Math.random() * 5000));
        return speed;
    }

    @Override
    int range() {
        int range = (3000 + (int) (Math.random() * 50000));
        return range;
    }

    @Override
    int weight() {
        int weight = (50000 + (int) (Math.random() * 100000));
        return weight;
    }

    @Override
    int fuel() {
        int fuel = (100 + (int) (Math.random() * 1000));
        return fuel;
    }

    @Override
    int number() {
        int number = (1 + (int) (Math.random() * 1000));
        return number;
    }

    @Override
    public int outBak() {
        int outBak = 100;
        return outBak;
    }

    @Override
    int tank() {
        int tank = (1500 + (int) (Math.random() * 2000));
        return tank;
    }

    @Override
    int passenger() {
        int passenger = (0 + (int) (Math.random() * 20));
        return passenger;
    }

    @Override
    int passengerSeat() {
        int passenger_seat = (100 + (int) (Math.random() * 300));

        return passenger_seat;
    }

}