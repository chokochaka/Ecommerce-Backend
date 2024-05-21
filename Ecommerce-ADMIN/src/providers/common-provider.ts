import type {DataProvider} from "@refinedev/core";
import axios from "axios";
import {GetOnePayLoad, GetSearchPayLoad} from "../apis/ProductItemRequests";

const API_URL = "http://localhost:8080/api/v1";


export const CommonProvider: DataProvider = {
    getApiUrl: () => API_URL,

    getOne: async ({resource, id}) => {
        const requestPayload = GetOnePayLoad(id.toString());
        const response = await axios.post(
            `${API_URL}/${resource}/search`, requestPayload
        );
        if (response.status < 200 || response.status > 299) throw response;
        return {data: response?.data[0]};
    },

    update: async ({resource, id, variables}) => {
        const response = await axios.put(
            `${API_URL}/${resource}/${id}`, variables
        );

        if (response.status < 200 || response.status > 299) throw response;
        return {
            data: response?.data,
        };
    },
    getList: async ({resource, pagination, filters, sorters}) => {
        const fieldRequestDtos: {
            field: string;
            operator: string;
            value: string;
        }[] = [];
        if (filters && filters.length > 0) {
            filters.forEach((filter) => {
                fieldRequestDtos.push(
                    {
                        field: "field" in filter ? filter.field : "",
                        operator: filter.operator.toUpperCase(),
                        value: filter.value,
                    }
                )
            });
        }
        const requestPayload = GetSearchPayLoad(
            {
                fieldRequestDtos: fieldRequestDtos,
                pageRequestDto: {
                    pageNo: pagination?.current as number,
                    pageSize: pagination?.pageSize as number,
                    sort: sorters ? sorters[0]?.order.toUpperCase() : "",
                    sortByColumn: sorters ? sorters[0]?.field : "",
                },
            },
        );
        const response = await axios.post(
            `${API_URL}/${resource}/search/paginated`, requestPayload
        );

        if (response.status < 200 || response.status > 299) throw response;

        return {
            data: response?.data.content,
            total: response?.data.totalElements, // We'll cover this in the next steps.
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
    /* ... */
};