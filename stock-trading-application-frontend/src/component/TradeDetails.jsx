import React, { useState, useEffect } from 'react';
import { addTradeDetails, deleteTradeDetails, getAllTradeDetails, updateTradeDetails } from './utils/APIFunction';
import { Link } from 'react-router-dom';
import Pagination from './Pagination';

const TradeDetails = () => {

    const [trades, setTrades] = useState([]);
    const[useSuccessMessage,setSuccessMessage] = useState("");
    const[useErrorMessage,setErrorMessage] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [TradeDetailsPerPage] = useState(4);
    const [form, setForm] = useState({
        tradeDateTime: '',
        stockSymbol:'',
        stockName:'',
        listingPrice: '',
        quantity:'',
        type: 'BUY',
        pricePerUnit:'',
    });
    const [editingId, setEditingId] = useState(null);


    useEffect(() => {
        fetchTrades();
    }, []);

    

    const fetchTrades = async () => {
        const response = await getAllTradeDetails();
        setTrades(response.data);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prevForm) => ({ ...prevForm, [name]: value }));
    };
    
      
    const handleSubmit = async (e) => {
        e.preventDefault();
        
        if (editingId) {
            const status = await updateTradeDetails(editingId,form.tradeDateTime,form.stockSymbol,form.stockName,
                form.listingPrice,form.quantity,form.type,form.pricePerUnit);
            setEditingId(null);
            if(status == 200){
                setSuccessMessage("Trade Details Updated Successfully")
            }else{
                setErrorMessage("Trade Details Not Updated !!!")
            }
        } else {
            const status = await addTradeDetails(form.tradeDateTime,form.stockSymbol,form.stockName,
                form.listingPrice,form.quantity,form.type,form.pricePerUnit)

                if(status){
                    setSuccessMessage("Trade Details Added Successfully !!")
                    fetchTrades();
                }else{
                    setErrorMessage("Trade Details Not Added Some issuse Occur")
                }
        }
        setForm({
            tradeDateTime: '',
            stockSymbol: '',
            stockName: '',
            listingPrice: '',
            quantity: '',
            type: 'BUY',
            pricePerUnit: '',
        });
        fetchTrades();
    };
    
    setTimeout(()=>{
        setSuccessMessage("")
        setErrorMessage("")
    },5000)

    const handleEdit = (trade) => {
        setForm({ ...trade, tradeDateTime: trade.tradeDateTime.slice(0, 16) });
        setEditingId(trade.id);
    };

    const handleCreateOrder=(tradId)=>{
        
        console.log(tradId)
    }

    const handleDelete = async (id) => {
        const status = await deleteTradeDetails(id)
        if(status==200){
            setSuccessMessage("Trade Details Deleted Successfully")
            setErrorMessage("")
        }else{
            setErrorMessage("Trade Details Not Deleated")
            setSuccessMessage("")
        }
        fetchTrades();
    };

    //Calculate the current trades to display
    const indexOfLastOrder = currentPage * TradeDetailsPerPage;
    const indexOfFirstOrder = indexOfLastOrder - TradeDetailsPerPage;
    const currentTrades = trades.slice(indexOfFirstOrder, indexOfLastOrder);

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <div className='mt-4 d-flex flex-column align-items-center justify-content-center'>
            <div className='d-flex align-items-center gap-4'>
                <h2 className='mb-4'>Trade Details</h2> 
                <span className='h4 mb-3'><Link to={"/order-grid"}>View All Orders</Link></span>
            </div>
            <div className='container col-md-8 col-lg-6'>
                {useSuccessMessage && <p className='alert alert-success mt-5'>{useSuccessMessage}</p>}
                {useErrorMessage && <p className='alert alert-danger mt-5'>{useErrorMessage}</p>}
            </div>
            <form onSubmit={handleSubmit}>
                <input
                    name="tradeDateTime"
                    type="datetime-local"
                    value={form.tradeDateTime}
                    onChange={handleChange}
                    required
                />
                <input
                    name="stockSymbol"
                    value={form.stockSymbol}
                    onChange={handleChange}
                    placeholder="Stock Symbol"
                    required
                />
                <input
                    name="stockName"
                    value={form.stockName}
                    onChange={handleChange}
                    placeholder="Stock Name"
                    required
                />
                <input
                    name="listingPrice"
                    type="number"
                    value={form.listingPrice}
                    onChange={handleChange}
                    placeholder="Listing Price"
                    required
                />
                <input
                    name="quantity"
                    type="number"
                    value={form.quantity}
                    onChange={handleChange}
                    placeholder="Quantity"
                    required
                />
                <input
                    name="pricePerUnit"
                    type="number"
                    value={form.pricePerUnit}
                    onChange={handleChange}
                    placeholder="Price Per Unit"
                    required
                />
                <select name="type" value={form.type} onChange={handleChange}>
                    <option value="BUY">Buy</option>
                    <option value="SELL">Sell</option>
                </select>
                <button type="submit">{editingId ? 'Update' : 'Add'}</button>
            </form>
            
            <table class="table align-middle mt-4">
                    <thead className='table-dark'>
                      <tr>
                        <th scope="col">S.No</th>
                        <th scope="col">Date and Time</th>
                        <th scope="col">Symbol</th>
                        <th scope="col">Name</th>
                        <th scope="col">Listing Price</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price Per Unit</th>
                        <th scope="col">Type</th>
                        <th scope="col">Action</th>
                      </tr>
                    </thead>
                    {currentTrades.map((trade) => (
                    <tbody class="table-group-divider">
                      <tr>
                        <th>{trade.id}</th>
                        <td>{trade.tradeDateTime}</td>
                        <td>{trade.stockSymbol}</td>
                        <td>{trade.stockName}</td>
                        <td>{trade.listingPrice}</td>
                        <td>{trade.quantity}</td>
                        <td>{trade.pricePerUnit}</td>
                        <td>{trade.type}</td>
                        <td>
                            <button onClick={() => handleEdit(trade)}>Edit</button>
                            <button onClick={() => handleDelete(trade.id)}>Delete</button>
                            <p><a href={`place-order/${trade.id}`} className="link-underline-light" onClick={(e) => handleCreateOrder(e, trade.id)}>Place Order</a></p>
                        </td>
                      </tr>
                    </tbody>
                    
                ))}
            
            </table>
            <Pagination
                ordersPerPage={TradeDetailsPerPage}
                totalOrders={trades.length}
                paginate={paginate}
            />
        </div>
    );
};

export default TradeDetails;
