import React, { useState, useEffect } from 'react';
import { cancelOrder, createOrder, getOrderList, updateOrder } from './utils/APIFunction';
import { Link, useParams } from 'react-router-dom';
import Pagination from './Pagination';


function OrderMaster() {
  const [orders, setOrders] = useState([]);
  const [newOrder, setNewOrder] = useState({
    orderQuantity: '',
    orderDateTime:''
  });

  const[useSuccessMessage,setSuccessMessage] = useState("");
  const[useErrorMessage,setErrorMessage] = useState("");
  const [editingId, setEditingId] = useState(null);

  const [currentPage, setCurrentPage] = useState(1);
  const [orderPerPage] = useState(6);

   //Calculate the current trades to display
   const indexOfLastOrder = currentPage * orderPerPage;
   const indexOfFirstOrder = indexOfLastOrder - orderPerPage;
   const currentOrders = orders.slice(indexOfFirstOrder, indexOfLastOrder);

   const paginate = (pageNumber) => setCurrentPage(pageNumber);


  // Fetch orders from the backend
  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      const response = await getOrderList();
      setOrders(response.data);
    } catch (error) {
        console.error('Failed to fetch orders:', error);
    }
};

const handleChange = (e) => {
    const { name, value } = e.target;
    setNewOrder(prev => ({ ...prev, [name]: value }));
};

const {tradeId} = useParams();

const handleSubmit = async (e) => {
    e.preventDefault();
    
        if(editingId){
            const status = await updateOrder(editingId,newOrder.orderQuantity,newOrder.orderDateTime);
                fetchOrders()//refresh the list of orders after updating
            setEditingId(null);
            if(status == 202){
                setSuccessMessage("Order Updated Successfully");
            }else{
                setErrorMessage("Error in Updating room "+status)
            }
        }else{
            await createOrder(parseInt(tradeId),newOrder.orderQuantity,newOrder.orderDateTime);
            setSuccessMessage("Order Created Successfully")
            fetchOrders(); // Refresh the list of orders after adding a new one
            setNewOrder({ 
                orderQuantity: '',
                orderDateTime:''
            }); // Reset form
        }

  };

  const handleEdit=(order)=>{
    setNewOrder({ ...order});
        setEditingId(order.orderId)
    }

  const handleDelete = async (id) => {
    try{
        const status = await cancelOrder(id);
        setSuccessMessage("Order Cancel Successfully "+status)
        setErrorMessage("")
        fetchOrders();
    }catch(error){
        setErrorMessage("In Canceling order Some error Occur "+error)
        setSuccessMessage("")
    }
    
};

setTimeout(()=>{
    setSuccessMessage("")
    setErrorMessage("")
},4000)

  return (
    <div>
        <div className='d-flex align-items-center justify-content-center gap-3 m-3'>
            <h2>Create New Order</h2>
            <span className='h3'><Link to={"/trades"}>Trade Details</Link></span>
            <span className='h3'><Link to={"/order-grid"}>View Order In Grid Structure</Link></span>
        </div>

        <div className='container col-md-8 col-lg-6'>
                {useSuccessMessage && <p className='alert alert-success mt-5'>{useSuccessMessage}</p>}
                {useErrorMessage && <p className='alert alert-danger mt-5'>{useErrorMessage}</p>}
        </div>
    <div className='d-flex align-items-center justify-content-center'> 
      <form onSubmit={handleSubmit}>
        <input
          name="orderQuantity"
          type='number'
          value={newOrder.orderQuantity}
          onChange={handleChange}
          placeholder="Order Quantity"
          required
        />
        <input
          name="orderDateTime"
          type="datetime-local"
          value={newOrder.orderDateTime}
          onChange={handleChange}
          placeholder="order Date Time"
          required
        />
        
        <button type="submit">{editingId ?'Update order':'Add Order'}</button>
      </form>
    </div>
        <table class="table align-middle mt-4">
                    <thead className='table-dark'>
                      <tr>
                        <th scope="col">S.No</th>
                        <th scope="col">Date and Time</th>
                        <th scope="col">Symbol</th>
                        <th scope="col">Name</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price Per Unit</th>
                        <th scope="col">Total Price</th>
                        <th scope="col">Action</th>
                      </tr>
                    </thead>
                    {currentOrders.map((order) => (
                    <tbody class="table-group-divider">
                      <tr>
                        <th>{order.orderId}</th>
                        <td>{order.orderDateTime}</td>
                        <td>{order.stockSymbol}</td>
                        <td>{order.stockName}</td>
                        <td>{order.orderQuantity}</td>
                        <td>{order.perUnitPrice}</td>
                        <td>{order.totalPrice}</td>
                        <td>
                            <button onClick={() => handleEdit(order)}>Edit</button>
                            <button onClick={() => handleDelete(order.orderId)}>Delete</button>
                        </td>
                      </tr>
                    </tbody>                    
                ))}
            
            </table>

            <Pagination
                ordersPerPage={orderPerPage}
                totalOrders={orders.length}
                paginate={paginate}
            />

    </div>
  );
}

export default OrderMaster;
