import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    List<String> selectedItems = new ArrayList<>();
    public RestaurantTest() {
         this.openingTime= LocalTime.parse("10:30:00");
        this.closingTime= LocalTime.parse("22:00:00");
        this.restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }

    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockedRestaurant = Mockito.spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        LocalTime validTime = LocalTime.parse("17:00:00");
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(validTime);
        assertTrue(mockedRestaurant.isRestaurantOpen());
        //WRITE UNIT TEST CASE HERE
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant mockedRestaurant = Mockito.spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        LocalTime validTime = LocalTime.parse("23:00:00");
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(validTime);
        assertFalse(mockedRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void order_total_should_be_zero_when_no_items_selected(){
        selectedItems.add("");
        assertEquals(0,restaurant.getTotal(selectedItems));
    }

    @Test
    public void order_total_should_return_valid_sum_on_adding_items(){
        Item mockedItems1 = Mockito.spy(new Item("Sweet corn soup",119));
        Item mockedItems2 = Mockito.spy(new Item("Juice",250));
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Juice");
        int price1 =150;
        int price2= 200;
        int sum=price1+price2;
        Mockito.when(mockedItems1.getPrice()).thenReturn(price1);
        Mockito.when(mockedItems2.getPrice()).thenReturn(price2);
        assertEquals(sum,restaurant.getTotal(selectedItems));
    }



}