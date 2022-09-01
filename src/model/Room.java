package model;

import java.util.Objects;


public class Room implements IRoom {
    private String roomNumber;
    private Double price;
    private RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrices() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + " " +
                roomType.toString().toLowerCase() + " bed Room" +
                " Price: $" + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;

        return Objects.equals(roomNumber, room.roomNumber);

        /*
        return Objects.equals(roomNumber, room.roomNumber) &&
                Objects.equals(price, room.price) &&
                roomType == room.roomType;

         */


    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
        //return Objects.hash(roomNumber, price, roomType);
    }
}
