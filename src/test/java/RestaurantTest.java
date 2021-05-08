import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    //Class Level Variables
    LocalTime openingTime;
    LocalTime closingTime;
    LocalTime currentTime;
    Restaurant restaurant;
    int initialMenuSize;

    @BeforeEach
    public  void setup(){
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant =  new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        initialMenuSize = restaurant.getMenu().size();

    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        currentTime = LocalTime.parse("17:00:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTime);

        assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        LocalTime currentTime = LocalTime.parse("23:00:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTime);

        assertFalse(spiedRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Cost<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void calling_get_total_Order_cost_with_selected_items_from_menu_should_return_sum_of_total_cost_of_the_items(){

        int totalOrderCost = restaurant.getTotalOrderCost( new String[]{"Sweet corn soup","Vegetable lasagne"});
        assertEquals(388,totalOrderCost);

    }

    @Test
    public void calling_get_total_Order_cost_without_any_selected_items_should_return_total_Order_Cost_zero(){

        int totalOrderCost = restaurant.getTotalOrderCost( new String[0]);
        assertEquals(0,totalOrderCost);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<Cost>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}