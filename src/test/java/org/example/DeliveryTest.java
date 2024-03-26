package org.example;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.example.DeliveryEntity;
import java.sql.PreparedStatement;
import javax.persistence.PersistenceException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryTest extends AbstractTest {

    @Test
    public void testCreateDelivery() {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO delivery (order_id, courier_id, date_arrived, taken, payment_method) VALUES (16, 3, '2023-02-26 19:30:00', 'Yes', 'Card')");
            preparedStatement.executeUpdate();

            PreparedStatement selectStatement = getConnection().prepareStatement("SELECT * FROM delivery WHERE delivery_id = (SELECT MAX(delivery_id) FROM delivery)");
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                DeliveryEntity deliveryEntity = new DeliveryEntity();
                deliveryEntity.setDeliveryId(resultSet.getShort("delivery_id"));
                deliveryEntity.setDateArrived(resultSet.getString("date_arrived"));
                deliveryEntity.setTaken(resultSet.getString("taken"));
                deliveryEntity.setPaymentMethod(resultSet.getString("payment_method"));

                assertEquals("2023-02-26 19:30:00", deliveryEntity.getDateArrived());
                assertEquals("Yes", deliveryEntity.getTaken());
                assertEquals("Card", deliveryEntity.getPaymentMethod());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadDelivery() {
        try {
            PreparedStatement selectStatement = getConnection().prepareStatement("SELECT * FROM delivery WHERE delivery_id = 1");
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                DeliveryEntity deliveryEntity = new DeliveryEntity();
                deliveryEntity.setDeliveryId(resultSet.getShort("delivery_id"));
                deliveryEntity.setDateArrived(resultSet.getString("date_arrived"));
                deliveryEntity.setTaken(resultSet.getString("taken"));
                deliveryEntity.setPaymentMethod(resultSet.getString("payment_method"));

                assertEquals("2023-02-26 17:59:15", deliveryEntity.getDateArrived());
                assertEquals("Yes", deliveryEntity.getTaken());
                assertEquals("Cash", deliveryEntity.getPaymentMethod());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateDelivery() {
        try {
            PreparedStatement updateStatement = getConnection().prepareStatement("UPDATE delivery SET taken = 'No' WHERE delivery_id = 2");
            updateStatement.executeUpdate();

            PreparedStatement selectStatement = getConnection().prepareStatement("SELECT * FROM delivery WHERE delivery_id = 2");
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                DeliveryEntity deliveryEntity = new DeliveryEntity();
                deliveryEntity.setDeliveryId(resultSet.getShort("delivery_id"));
                deliveryEntity.setDateArrived(resultSet.getString("date_arrived"));
                deliveryEntity.setTaken(resultSet.getString("taken"));
                deliveryEntity.setPaymentMethod(resultSet.getString("payment_method"));

                assertEquals("No", deliveryEntity.getTaken());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteDelivery() {
        try {
            PreparedStatement deleteStatement = getConnection().prepareStatement("DELETE FROM delivery WHERE delivery_id = 3");
            deleteStatement.executeUpdate();

            PreparedStatement selectStatement = getConnection().prepareStatement("SELECT * FROM delivery WHERE delivery_id = 3");
            ResultSet resultSet = selectStatement.executeQuery();

            assertEquals(false, resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}