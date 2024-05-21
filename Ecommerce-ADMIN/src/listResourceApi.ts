export const listResourceApi = [
    {
        name: "blog_posts",
        list: "/blog-posts",
        create: "/blog-posts/create",
        edit: "/blog-posts/edit/:id",
        show: "/blog-posts/show/:id",
        meta: {
            canDelete: true,
        },
    },
    {
        name: "category",
        list: "/category",
        create: "/category/create",
        edit: "/category/edit/:id",
        show: "/category/show/:id",
        meta: {
            canDelete: true,
        },
    },
    {
        name: "productItem",
        list: "/productItem",
        edit: "/productItem/:id/edit",
        meta: {
            canDelete: true,
            label: "Product Items"
        },
    },
    {
        name: "product",
        list: "/product",
        create: "/product/create",
        edit: "/product/:id/edit",
        show: "/product/show/:id",
        meta: {
            canDelete: true,
            label: "Product"
        },
    },
];