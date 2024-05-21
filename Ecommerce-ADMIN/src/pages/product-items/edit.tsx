import {Box, Stack, TextField, Typography} from "@mui/material";
import {DateField, Edit} from "@refinedev/mui";
import {useForm} from "@refinedev/react-hook-form";
import React from "react";

export const ProductItemEdit = () => {
    const {
        saveButtonProps,
        refineCore: {queryResult, formLoading}, // queryResult?.data?.data
        register,
        formState: {errors},
    } = useForm({});

    const data = queryResult?.data?.data;

    if (formLoading) {
        return <div>Loading...</div>;
    }
    return (
        <>
            <Edit isLoading={formLoading} saveButtonProps={saveButtonProps}>
                <Box
                    component="form"
                    sx={{display: "flex", flexDirection: "column"}}
                    autoComplete="off"
                >
                    <TextField
                        {...register("price")}
                        error={!!(errors as any)?.price}
                        helperText={(errors as any)?.price?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{shrink: true}}
                        type="text"
                        label={"Price"}
                        name="price"
                    />
                    <TextField
                        {...register("imageUrl")}
                        error={!!(errors as any)?.imageUrl}
                        helperText={(errors as any)?.imageUrl?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{shrink: true}}
                        multiline
                        label={"Image"}
                        name="image"
                        rows={4}
                    />
                    <TextField
                        {...register("availableStock")}
                        error={!!(errors as any)?.availableStock}
                        helperText={(errors as any)?.availableStock?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{shrink: true}}
                        type="text"
                        label={"Available Stock"}
                        name="availableStock"
                    />
                    <TextField
                        {...register("variationCombination")}
                        error={!!(errors as any)?.variationCombination}
                        helperText={(errors as any)?.variationCombination?.message}
                        margin="normal"
                        fullWidth
                        InputLabelProps={{shrink: true}}
                        type="text"
                        label={"Variation"}
                        name="variationCombination"
                    />
                </Box>
            </Edit>
            <br/>
            <Stack gap={1}>
                <Typography variant="body1" fontWeight="bold">
                    {"ID"} : {data?.id}
                </Typography>
                <Typography variant="body1" fontWeight="bold">
                    {"Product ID"} : {data?.productId}
                </Typography>
                <Typography variant="body1" fontWeight="bold">
                    {"Created On"}
                </Typography>
                <DateField
                    format={"DD:MM:YYYY HH:mm hh:mm:ss A"}
                    value={data?.createdOn}/>
                <Typography variant="body1" fontWeight="bold">
                    {"Last Updated On"}
                </Typography>
                <DateField
                    format={"DD:MM:YYYY HH:mm hh:mm:ss A"}
                    value={data?.lastUpdatedOn}/>
            </Stack>
        </>
    );
};
