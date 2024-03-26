package org.example;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.example.CourierInfoEntity;

import javax.persistence.PersistenceException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CourierTest extends AbstractTest {

    @Test
    public void testReadCourierInfo() {
        try (Session session = getSession()) {
            Query query = session.createQuery("FROM CourierInfoEntity WHERE courierId = :id");
            query.setParameter("id", 1);
            CourierInfoEntity courier = (CourierInfoEntity) query.getSingleResult();

            // Assert statements to verify the retrieved courier information
            assertNotNull(courier);
            assertEquals("John", courier.getFirstName());
            assertEquals("+ 7 960 655 0954", courier.getPhoneNumber());
            assertEquals("foot", courier.getDeliveryType());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCreateCourierInfo() {
        try (Session session = getSession()) {
            CourierInfoEntity newCourier = new CourierInfoEntity();
            newCourier.setFirstName("Alice");
            newCourier.setLastName("Smith");
            newCourier.setPhoneNumber(" + 7 960 655 0800");
            newCourier.setDeliveryType("bike");

            session.beginTransaction();
            session.save(newCourier);
            session.getTransaction().commit();


            assertNotNull(newCourier.getCourierId());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateCourierInfo() {
        try (Session session = getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM CourierInfoEntity WHERE firstName = :name");
            query.setParameter("name", "Kate");
            CourierInfoEntity courier = (CourierInfoEntity) query.getSingleResult();

            courier.setDeliveryType("bike");
            session.update(courier);
            session.getTransaction().commit();


            assertEquals("bike", courier.getDeliveryType());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteCourierInfo() {
        try (Session session = getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM CourierInfoEntity WHERE lastName = :name");
            query.setParameter("name", "Kolaris");
            CourierInfoEntity courier = (CourierInfoEntity) query.getSingleResult();

            session.delete(courier);
            session.getTransaction().commit();


            Query verifyQuery = session.createQuery("FROM CourierInfoEntity WHERE lastName = :name");
            verifyQuery.setParameter("name", "Kolaris");
            assertTrue(verifyQuery.getResultList().isEmpty());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}