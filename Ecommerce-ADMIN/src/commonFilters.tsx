import {GridFilterOperator} from "@mui/x-data-grid";

const inputStyle = {
    marginTop: '18px',
    marginLeft: '12px',
    padding: '5px',
    borderRadius: '4px',
    border: '1px solid #ccc',
    width: '90%',
};

export const commonFilterOperators: GridFilterOperator[] = [
    {
        label: 'Equal',
        value: 'eqm',
        getApplyFilterFn: (filterItem) => {
            if (!filterItem.value) {
                return null;
            }
            return ({value}) => {
                return value === filterItem.value;
            };
        },
        InputComponent: (props) => (
            <input
                style={inputStyle}
                type="text"
                value={props.item.value || ''}
                onChange={(e) => props.applyValue({...props.item, value: e.target.value})}
            />
        ),
    },
    {
        label: 'Contains',
        value: 'contains',
        getApplyFilterFn: (filterItem) => {
            if (!filterItem.value) {
                return null;
            }
            return ({value}) => {
                return value.includes(filterItem.value);
            };
        },
        InputComponent: (props) => (
            <input
                style={inputStyle}
                type="text"
                value={props.item.value || ''}
                onChange={(e) => props.applyValue({...props.item, value: e.target.value})}
            />
        ),
    },
    {
        label: 'Greater than',
        value: 'gt',
        getApplyFilterFn: (filterItem) => {
            if (!filterItem.value) {
                return null;
            }
            return ({value}) => {
                return Number(value) > Number(filterItem.value);
            };
        },
        InputComponent: (props) => (
            <input
                style={inputStyle}
                type="number"
                value={props.item.value || ''}
                onChange={(e) => props.applyValue({...props.item, value: e.target.value})}
            />
        ),
    },
    {
        label: 'Less than',
        value: 'lt',
        getApplyFilterFn: (filterItem) => {
            if (!filterItem.value) {
                return null;
            }
            return ({value}) => {
                return Number(value) < Number(filterItem.value);
            };
        },
        InputComponent: (props) => (
            <input
                style={inputStyle}
                type="number"
                value={props.item.value || ''}
                onChange={(e) => props.applyValue({...props.item, value: e.target.value})}
            />
        ),
    },
    {
        label: 'Starts with',
        value: 'startsWith',
        getApplyFilterFn: (filterItem) => {
            if (!filterItem.value) {
                return null;
            }
            return ({value}) => {
                return value.startsWith(filterItem.value);
            };
        },
        InputComponent: (props) => (
            <input
                style={inputStyle}
                type="text"
                value={props.item.value || ''}
                onChange={(e) => props.applyValue({...props.item, value: e.target.value})}
            />
        ),
    },
    {
        label: 'Ends with',
        value: 'endsWith',
        getApplyFilterFn: (filterItem) => {
            if (!filterItem.value) {
                return null;
            }
            return ({value}) => {
                return value.endsWith(filterItem.value);
            };
        },
        InputComponent: (props) => (
            <input
                style={inputStyle}
                type="text"
                value={props.item.value || ''}
                onChange={(e) => props.applyValue({...props.item, value: e.target.value})}
            />
        ),
    },
    {
        label: 'In',
        value: 'inm',
        getApplyFilterFn: (filterItem) => {
            if (!filterItem.value) {
                return null;
            }
            const values = filterItem.value.split(',').map((v: string) => v.trim());
            return ({value}) => {
                return values.includes(value);
            };
        },
        InputComponent: (props) => (
            <input
                style={inputStyle}
                type="text"
                placeholder="1,2,3"
                value={props.item.value || ''}
                onChange={(e) => props.applyValue({...props.item, value: e.target.value})}
            />
        ),
    },
    {
        label: 'Between',
        value: 'between',
        getApplyFilterFn: (filterItem) => {
            if (!filterItem.value) {
                return null;
            }
            const [start, end] = filterItem.value.split(',').map((v: string) => Number(v.trim()));
            return ({value}) => {
                return Number(value) >= start && Number(value) <= end;
            };
        },
        InputComponent: (props) => (
            <input
                style={inputStyle}
                type="text"
                placeholder="10,20"
                value={props.item.value || ''}
                onChange={(e) => props.applyValue({...props.item, value: e.target.value})}
            />
        ),
    },
];