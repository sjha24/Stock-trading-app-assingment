import logo from './logo.svg';
import './App.css';
import TradeDetails from './component/TradeDetails';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import OrderMaster from './component/OrderMaster';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import TradeOrdersGrid from './component/TradeOrdersGrid';

function App() {
  return (
    <Router>
    <Routes>
      <Route path="/trades" element={<TradeDetails />} />
      <Route path="/place-order/:tradeId" element={<OrderMaster/>} />
      <Route path='order-grid' element={<TradeOrdersGrid/>}/>
    </Routes>
  </Router>
  );
}

export default App;
