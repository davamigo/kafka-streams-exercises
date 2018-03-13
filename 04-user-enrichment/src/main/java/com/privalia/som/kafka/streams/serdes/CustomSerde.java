package com.privalia.som.kafka.streams.serdes;

import com.privalia.som.kafka.streams.entity.Product;
import com.privalia.som.kafka.streams.entity.Purchase;
import com.privalia.som.kafka.streams.entity.User;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

/**
 * Custom serde (SERializer/DEserializer)
 *
 * @author david.amigo
 */
public class CustomSerde extends Serdes {

    /**
     * @return new serde for User entity
     */
    public static Serde<User> forUser() {
        return new ForUser();
    }

    /**
     * @return new serde for Product entity
     */
    public static Serde<Product> forProduct() {
        return new ForProduct();
    }

    /**
     * @return new serde for Purchase entity
     */
    public static Serde<Purchase> forPurchase() {
        return new ForPurchase();
    }

    /**
     * Custom serde subclass for User entity
     */
    public static final class ForUser extends Serdes.WrapperSerde<User> {
        public ForUser() {
            super(new CustomSerializer.ForUser(), new CustomDeserializer.ForUser());
        }
    }

    /**
     * Custom serde subclass for Product entity
     */
    public static final class ForProduct extends Serdes.WrapperSerde<Product> {
        public ForProduct() {
            super(new CustomSerializer.ForProduct(), new CustomDeserializer.ForProduct());
        }
    }

    /**
     * Custom serde subclass for Purchase entity
     */
    public static final class ForPurchase extends Serdes.WrapperSerde<Purchase> {
        public ForPurchase() {
            super(new CustomSerializer.ForPurchase(), new CustomDeserializer.ForPurchase());
        }
    }
}
