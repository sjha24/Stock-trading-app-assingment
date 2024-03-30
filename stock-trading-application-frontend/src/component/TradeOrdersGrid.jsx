import React, { useState, useEffect } from 'react';
import { getOrderList } from './utils/APIFunction';
import { Link} from 'react-router-dom';
import '../App.css';
import Pagination from './Pagination';

function TradeOrdersGrid() {
    const [tradeOrders, setTradeOrders] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [ordersPerPage] = useState(6);

    
    useEffect(() => {
        const fetchTradeOrders = async () => {
            const res =   await getOrderList();
            const fetchedOrders = res.data;
            setTradeOrders(fetchedOrders);
        };
        
        fetchTradeOrders();
    }, []);
    
    // Calculate the current trades to display
    const indexOfLastOrder = currentPage * ordersPerPage;
    const indexOfFirstOrder = indexOfLastOrder - ordersPerPage;
    const currentOrders = tradeOrders.slice(indexOfFirstOrder, indexOfLastOrder);
    
    // Change page
    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <div className='d-flex flex-column justify-content-center align-items-center'>
            <div>
                <div className='d-flex aling-items-center justify-content-center m-3 gap-2'>
                    <h4>All Orders</h4>
                    <span className='h4'><Link to={"/trades"}>Trade Details</Link></span>
                </div>
                <div className="grid">
                    {currentOrders.map(order => (
                        <div className = "backgroundChange" key={order.id}>
                            <p>Symbol - {order.stockSymbol}</p>
                            <p>Name - {order?.stockName}</p>
                            <p>Quantity - {order.orderQuantity}</p>
                            <p>Type - {order.type}</p>
                            <p>Total Price - {order.totalPrice}</p>
                            <p>Date Time - {order.orderDateTime}</p>
                        </div>
                    ))}
                </div>
            <Pagination
                ordersPerPage={ordersPerPage}
                totalOrders={tradeOrders.length}
                paginate={paginate}
            />
            </div>
        </div>
    );
}

export default TradeOrdersGrid;
