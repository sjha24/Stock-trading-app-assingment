# Stock-trading-app-assingment
## Project Summary - This project involves creating a stock trading application using Java, Spring Boot for the backend, and React JS for the frontend. It features two main components: trade details and order management. Users can add, update, and delete trades, including fields like trade date/time, stock symbol, listing price, quantity, type (buy/sell), and price per unit. An order can be created from listed trades, which are then persisted in an order table with a status of "created". The frontend includes dynamic components for managing trade details and orders, with capabilities for listing orders in a paginated view. The project emphasizes CRUD operations, relational database management, and interactive UI design.

## Trade Details - 
   ```
    The trade details component of this project allows users to manage stock trades,
    offering features to input and modify information such as trade date/time, stock symbol,
    type (buy or sell), quantity, and prices. It emphasizes user interaction for effective
    trading strategy implementation within a dynamic and responsive interface.
   ```

## Trade Details API end Points - 
  @RequestMapping("tradeDetails)

  * AddTradeDetails -
   @PostMapping("/add")
  ```
  [{"key":"tradeDateTime","value":"2024-03-28T15:20:30"},
  {"key":"stockSymbol","value":"AAPL"},
  {"key":"listingPrice","value":"119.00"},
  {"key":"quantity","value":"1000"},
  {"key":"type","value":"SELL"},
  {"key":"pricePerUnit","value":"115.50"},
  {"key":"stockName","value":"sadh",
  ]
  ```
* updateTradeDetails -
   @PutMapping("/update/id")
  ```
   [{"key":"tradeDateTime","value":"2024-03-28T15:20:30"},
  {"key":"stockSymbol","value":"AAPL"},
  {"key":"listingPrice","value":"119.00"},
  {"key":"quantity","value":"1000"},
  {"key":"type","value":"SELL"},
  {"key":"pricePerUnit","value":"115.50"},
  {"key":"stockName","value":"sadh",
  }  
  ```
  * DeleteTradeDetails -
    
    @DeleteMapping("/delete/id")
    
  * getAllTradeDetails -
     @GetMapping("/all")
![Screenshot (320)](https://github.com/sjha24/Stock-trading-app-assingment/assets/98340874/76e03bf5-c700-461c-b33d-bc54b3e0d142)


![Screenshot (319)](https://github.com/sjha24/Stock-trading-app-assingment/assets/98340874/a51c2e33-3168-482c-8c07-a85ca826bba8)

## OrderMaster - 
  
  ```
  The Order Master entity in the project serves as a pivotal mechanism for order management,
  enabling CRUD operations on order data. It records and updates orders based on trade details.
  ```

 ## API for Oder Management API - 
 @RequestMapping("order)

 * createOrder
 * @PostMapping("order/create")
   ```
   [{"key":"tradeDetailsId","value":"1",},
   {"key":"orderQuantity","value":"30",},
   {"key":"orderDateTime","value":"2024-03-28T15:20:30",}]
   ```

 * UpdateOrder
   @PatchMapping("/update)
    ```
   [{"key":"Id","value":"1",},
   {"key":"orderQuantity","value":"30",},
   {"key":"orderDateTime","value":"2024-03-28T15:20:30",}]
   ```

   * CancelOrder
  * @DeleteMapping("/cancel/id")
    
  * getAllOrder
  * @GetMapping("/list)
![Screenshot (321)](https://github.com/sjha24/Stock-trading-app-assingment/assets/98340874/d00cfa99-2a45-4e64-a3d1-d8e70000f1c2)
