import {Button, FormControl, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import {SubmitHandler, useForm} from "react-hook-form";
import {useState} from "react";
import {useList} from "@refinedev/core";
import axios from "axios";

export const ProductCreate = () => {
    const [age, setAge] = useState('');
    const [selectedCategory, setSelectedCategory] = useState<string[]>([]);


    const {
        // refineCore: {onFinish},
        register,
        formState: {errors},
        handleSubmit
    } = useForm({});

    const {data, isLoading} = useList({
        resource: "category",
        sorters: [{field: "id", order: "asc"}],
    });

    if (isLoading) {
        return <div>Loading...</div>;
    }

    // const onFinishHandler = async (data: FieldValues) => {
    //     console.log(data)
    //
    //     await onFinish({});
    // };

    const onSubmit: SubmitHandler<any> = async (dataSubmit) => {
        dataSubmit.averageRating = 0;
        const submittedValue = await axios.post('http://localhost:8080/api/v1/product', dataSubmit)
        if (submittedValue.status === 200) {
            window.location.href = "/product";
        } else {
            alert("Failed to submit")
        }
    }


    return (
        // <Create isLoading={formLoading}
        //     // saveButtonProps={saveButtonProps}
        // >
        <form
            onSubmit={handleSubmit(onSubmit)}
            style={{display: "flex", flexDirection: "column"}}
        >
            <TextField
                {...register("name", {
                    required: "This field is required",
                })}
                error={!!(errors as any)?.title}
                helperText={(errors as any)?.title?.message}
                margin="normal"
                fullWidth
                InputLabelProps={{shrink: true}}
                type="text"
                label={"Name"}
                name="name"
            />
            <TextField
                {...register("description", {
                    required: "This field is required",
                })}
                error={!!(errors as any)?.content}
                helperText={(errors as any)?.content?.message}
                margin="normal"
                fullWidth
                InputLabelProps={{shrink: true}}
                multiline
                label={"Description"}
                name="description"
            />
            <TextField
                {...register("averageRating")}
                margin="normal"
                fullWidth
                InputLabelProps={{shrink: true}}
                label={"Average Rating"}
                name="averageRating"
                value={0}
                disabled={true}
            />
            <FormControl style={{marginTop: "12px"}} fullWidth>
                <InputLabel id="category-select-label">Category</InputLabel>
                <Select
                    labelId="category-select-label"
                    id="category"
                    multiple
                    value={selectedCategory}
                    label="Category"
                    {...register("categoryIds")}
                    onChange={(event) => setSelectedCategory(event.target.value as string[])}
                >
                    {data?.data.map((category) => (
                        <MenuItem key={category.id} value={category.id}>
                            {category.name}
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>

            <FormControl style={{marginTop: "25px"}} fullWidth>
                <InputLabel id="featured-select-label">Featured</InputLabel>
                <Select
                    labelId="featured-select-label"
                    id="featured"
                    value={age}
                    label="Featured"
                    {...register("isFeatured")}
                    onChange={(event) => setAge(event.target.value as string)}
                >
                    <MenuItem value={"true"}>True</MenuItem>
                    <MenuItem value={"false"}>False</MenuItem>
                </Select>
            </FormControl>
            <br/>
            <br/>
            <Button type={"submit"}>Submit</Button>
        </form>
        // </Create>
    );
};
