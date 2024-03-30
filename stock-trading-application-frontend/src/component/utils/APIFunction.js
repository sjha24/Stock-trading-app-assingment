import axios from "axios"

export const api = axios.create({
	baseURL: "http://localhost:8080"
})

export async function addTradeDetails(tradeDateTime,stockSymbol,stockName, listingPrice,quantity,type,pricePerUnit) {
	const formData = new FormData()
	formData.append("tradeDateTime",tradeDateTime)
	formData.append("stockSymbol",stockSymbol)
	formData.append("stockName",stockName)
	formData.append("listingPrice",listingPrice)
	formData.append("quantity",quantity )
	formData.append("type",type )
	formData.append("pricePerUnit",pricePerUnit)

	const response = await api.post("/tradeDetails/add", formData)
	if (response.status === 201) {
		return true
	} else {
		return false
	}
}

export async function updateTradeDetails(id,tradeDateTime,stockSymbol,stockName, listingPrice,quantity,type,pricePerUnit) {
	const formData = new FormData()
	formData.append("tradeDateTime",tradeDateTime)
	formData.append("stockSymbol",stockSymbol)
	formData.append("stockName",stockName)
	formData.append("listingPrice",listingPrice)
	formData.append("quantity",quantity )
	formData.append("type",type )
	formData.append("pricePerUnit",pricePerUnit)

	const response = await api.put(`/tradeDetails/update/${id}`, formData)
	return response.status
}

export async function deleteTradeDetails(id) {

	try {
		const result = await api.delete(`/tradeDetails/${id}`)
		return result.status
	} catch (error) {
		throw new Error(`Error in Deleting Trade Details :${error.message}`)
	}
}

export async function getAllTradeDetails(){
    const response = await api.get("/tradeDetails/all");
    return response;
}

export async function getOrderList(){
    const response = await api.get("/order/list");
    return response;
}

export async function createOrder(tradeDetailsId,orderQuantity,orderDateTime){
    const formData = new FormData();
    formData.append("tradeDetailsId",tradeDetailsId);
    formData.append("orderQuantity",orderQuantity)
    formData.append("orderDateTime",orderDateTime)
    const response = await api.post("/order/create",formData);
    return response
}

export async function cancelOrder(id){
	try {
		const result = await api.delete(`/order/cancel/${id}`)
		return result.status
	} catch (error) {
		throw new Error(`Error in Cancel Order :${error.message}`)
	}
}

export async function updateOrder(id,quantity,updateDateTime){
	const formData = new FormData();
	formData.append("quantity",quantity)
	formData.append("updateDateTime",updateDateTime)
	try {
		const result = await api.patch(`/order/update/${id}`,formData)
		return result.status
	} catch (error) {
		throw new Error(`Error in Updating Order Details :${error.message}`)
	}
}

