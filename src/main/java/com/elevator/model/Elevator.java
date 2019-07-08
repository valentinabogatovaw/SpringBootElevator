package com.elevator.model;

import java.util.concurrent.ConcurrentSkipListSet;

public class Elevator extends Thread {

    private int currentFloor = 1;
    private ConcurrentSkipListSet<Integer> requestedFloors = new ConcurrentSkipListSet<>();
    private Direction direction = Direction.UP;

    public static Elevator elevator;

    public static synchronized Elevator getInstance(){
        if (elevator == null){
            return new Elevator();
        }else{
            return elevator;
        }
    }

    public synchronized void addFloor(int floor){
            requestedFloors.add(floor);
            if(!Thread.currentThread().isInterrupted()){
                notify();
            }
    }

    public synchronized int nextFloor() throws InterruptedException {
        Integer next = null;
        switch (direction){
            case UP:
                if (requestedFloors.ceiling(currentFloor)!=null){
                    next = requestedFloors.ceiling(currentFloor);
                }else {
                    next = requestedFloors.floor(currentFloor);
                }
            case DOWN:
                if (requestedFloors.floor(currentFloor) != null){
                    next = requestedFloors.floor(currentFloor);
                }else{
                    next = requestedFloors.ceiling(currentFloor);
                }
        }

        if (next == null){
            System.out.println("Elevator is waiting at the floor " + getCurrentFloor());
            wait();
        }else{
            requestedFloors.remove(next);
        }

        return (next == null) ? -1 : next;
    }
    public int getCurrentFloor() {
        return currentFloor;
    }

    public ConcurrentSkipListSet<Integer> getRequestedFloors() {
        return requestedFloors;
    }

    public synchronized void setCurrentFloor(int next) throws InterruptedException {
        if (next == -1) {
            return;
        }else {
            if (getCurrentFloor() > next) {
                setDirection(Direction.DOWN);
                int cur = getCurrentFloor();
                for (int i = 1; i <= cur - next; ++i) {
                     wait(10000);
                    currentFloor = currentFloor - 1;
                    if (currentFloor == next) {
                        System.out.println();
                        wait(2000);
                        System.out.println("Welcome to floor " + next);
                    } else {
                        System.out.println("Current floor " + getCurrentFloor());
                    }
                }
            } else {
                int cur = getCurrentFloor();
                setDirection(Direction.UP);
                for (int i = 1; i <= next - cur; ++i) {
                    wait(10000);
                    currentFloor = currentFloor + 1;
                    if (currentFloor == next) {
                        wait(2000);
                        System.out.println("Welcome to floor " + next);
                    } else {
                        System.out.println("Current floor " + getCurrentFloor());
                    }
                }
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void run() {
        while (true) {
            try {
                setCurrentFloor(nextFloor());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

