import type {DataProvider} from "@refinedev/core";
import axios from "axios";
import {GetOnePayLoad} from "../apis/ProductItemRequests";

const API_URL = "http://localhost:8080/api/v1";


export const getProductItemsByProductId: DataProvider = {
    getApiUrl: () => API_URL,

    getOne: async ({resource, id}) => {
        const requestPayload = GetOnePayLoad(id.toString());
        const response = await axios.post(
            `${API_URL}/${resource}/search`, requestPayload
        );
        if (response.status < 200 || response.status > 299) throw response;
        window.location.reload();
        return {data: response?.data[0]};
    },
    update: async ({resource, id, variables}) => {
        const response = await axios.put(
            `${API_URL}/${resource}/${id}`, variables
        );

        if (response.status < 200 || response.status > 299) throw response;
        console.log(response)
        return {
            data: response?.data,
        };
    },
    getList: async ({meta}) => {
        const response = await axios.get(`${API_URL}/productItem/product/${meta?.id}`);

        if (response.status < 200 || response.status > 299) throw response;
        console.log(response)
        return {
            data: response?.data,
            total: response?.data?.length, // We'll cover this in the next steps.
        };
    },
    create: async ({resource, variables}) => {
        const response = await axios.post(
            `${API_URL}/${resource}`, variables
        );

        if (response.status < 200 || response.status > 299) throw response;

        return {
            data: response?.data,
        };
    },
    deleteOne: async ({resource, id}) => {
        const response = await axios.delete(
            `${API_URL}/${resource}/${id}`
        );
        if (response.status < 200 || response.status > 299) throw response;

        return {
            data: response?.data,
        };
    },
};