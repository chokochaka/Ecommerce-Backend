import {DataGrid, GridColDef} from "@mui/x-data-grid";
import {DateField, DeleteButton, EditButton, List, ShowButton, useDataGrid} from "@refinedev/mui";
import React from "react";
import {commonFilterOperators} from "../../commonFilters";

export const ProductList = () => {
    const {dataGridProps} = useDataGrid();

    const columns = React.useMemo<GridColDef[]>(
        () => [
            {
                field: "id",
                headerName: "ID",
                type: "number",
                minWidth: 40,
                maxWidth: 50,
                filterOperators: commonFilterOperators,
            },
            {
                field: "name",
                flex: 1,
                headerName: "Name",
                minWidth: 200,
                filterOperators: commonFilterOperators,
            },
            {
                field: "description",
                flex: 1,
                headerName: "Description",
                minWidth: 250,
                filterOperators: commonFilterOperators,
            },
            {
                field: "categories",
                flex: 1,
                headerName: "Categories",
                minWidth: 100,
                filterOperators: commonFilterOperators,
                renderCell: ({row}) => {
                    console.log(row)
                    const categoriesString = [];
                    for (let j = 0; j < row?.categories?.length; j++) {
                        categoriesString.push(row?.categories[j]?.name);
                    }
                    return <>{categoriesString.sort().join(", ").slice(0, 20) + "..." || ""}</>
                },
            },
            {
                field: "averageRating",
                flex: 1,
                headerName: "Average Rating",
                minWidth: 70,
                maxWidth: 120,
                align: "center",
                filterOperators: commonFilterOperators,
            },
            {
                field: "isFeatured",
                flex: 1,
                headerName: "Featured",
                minWidth: 50,
                maxWidth: 70,
                align: "center",
                filterOperators: commonFilterOperators,
                renderCell: ({row}) => (<>{row.featured ? "✔️" : "❌"}</>),
            },
            {
                field: "createdOn",
                flex: 1,
                headerName: "Created On",
                minWidth: 70,
                maxWidth: 100,
                filterOperators: commonFilterOperators,
                renderCell: ({value}) => <DateField value={value}/>,
            },
            {
                field: "lastUpdatedOn",
                flex: 1,
                headerName: "Last Updated",
                minWidth: 70,
                maxWidth: 100,
                filterOperators: commonFilterOperators,
                renderCell: ({value}) => <DateField value={value}/>,
            },
            {
                field: "actions",
                headerName: "Actions",
                sortable: false,
                renderCell: ({row}) => (
                    <>
                        <EditButton hideText recordItemId={row.id}/>
                        <ShowButton hideText recordItemId={row.id}/>
                        <DeleteButton hideText recordItemId={row.id}/>
                    </>
                ),
                align: "center",
                headerAlign: "center",
                minWidth: 80,
            },
        ],
        []
    );

    return (
        <List>
            <DataGrid {...dataGridProps} columns={columns} autoHeight/>
        </List>
    );
};
