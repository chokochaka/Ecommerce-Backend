const GetOnePayLoad = (id: string) => {
    return {
        globalOperator: "AND",
        fieldRequestDtos: [
            {
                field: "id",
                operator: "EQM",
                value: id,
            },
        ]
    };
};

interface GetSearchPayLoadInterface {
    fieldRequestDtos?: Array<{
        field: string;
        operator: string;
        value: string;
    }>;
    pageRequestDto: {
        pageNo: number,
        pageSize: number,
        sort: string,
        sortByColumn: string
    }
}

const GetSearchPayLoad = ({
                              fieldRequestDtos,
                              pageRequestDto
                          }: GetSearchPayLoadInterface) => {
    return {
        globalOperator: "AND",
        fieldRequestDtos,
        pageRequestDto,
    };
};

export {GetOnePayLoad, GetSearchPayLoad}