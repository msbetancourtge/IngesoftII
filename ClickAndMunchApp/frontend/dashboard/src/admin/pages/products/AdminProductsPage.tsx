import { Table, TableCaption, TableHeader, TableRow, TableHead, TableBody, TableCell } from "@/components/ui/table"

type ProductStatus = "Borrador" | "Pendiente" | "Publicado";

interface AdminProduct {
  id: string;
  name: string;
  price: string;
  stock: number;
  category: string;
  status: ProductStatus;
  image: string;
}

const statusColors: Record<ProductStatus, string> = {
  Borrador: "bg-gray-100 text-gray-800",
  Pendiente: "bg-amber-100 text-amber-800",
  Publicado: "bg-green-100 text-green-800",
};

const products: AdminProduct[] = [
  {
    id: "PRD-001",
    name: "Latte Vainilla",
    price: "$4.50",
    stock: 120,
    category: "Bebidas",
    status: "Publicado",
    image: "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=200&h=200&fit=crop&auto=format",
  },
  {
    id: "PRD-002",
    name: "Ensalada Mediterránea",
    price: "$9.80",
    stock: 45,
    category: "Ensaladas",
    status: "Pendiente",
    image: "https://images.unsplash.com/photo-1550304943-4f24f54ddde9?w=200&h=200&fit=crop&auto=format",
  },
  {
    id: "PRD-003",
    name: "Burger Doble",
    price: "$12.00",
    stock: 30,
    category: "Platos fuertes",
    status: "Publicado",
    image: "https://images.unsplash.com/photo-1550547660-d9450f859349?w=200&h=200&fit=crop&auto=format",
  },
  {
    id: "PRD-004",
    name: "Cheesecake Frutos Rojos",
    price: "$6.40",
    stock: 25,
    category: "Postres",
    status: "Borrador",
    image: "https://images.unsplash.com/photo-1505253758473-96b7015fcd40?w=200&h=200&fit=crop&auto=format",
  },
];

export const AdminProductsPage = () => {
  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-2">
        <h1 className="text-2xl font-bold text-gray-900">Productos</h1>
        <p className="text-gray-600">Catálogo con estado de publicación, inventario y categoría.</p>
      </div>

      <div className="bg-white border border-gray-200 rounded-xl shadow-sm p-4">
        <Table>
          <TableCaption>Gestión de productos listos para aprobación y publicación.</TableCaption>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[120px]">ID</TableHead>
              <TableHead>Imagen</TableHead>
              <TableHead>Nombre</TableHead>
              <TableHead>Precio</TableHead>
              <TableHead>Inventario</TableHead>
              <TableHead>Categoria</TableHead>
              <TableHead>Estado</TableHead>
              <TableHead className="text-right">Acciones</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {products.map((product) => (
              <TableRow key={product.id}>
                <TableCell className="font-medium">{product.id}</TableCell>
                <TableCell>
                  <div className="w-16 h-16 rounded-lg overflow-hidden border border-gray-200 bg-gray-50">
                    <img
                      src={product.image}
                      alt={product.name}
                      className="w-full h-full object-cover"
                      loading="lazy"
                    />
                  </div>
                </TableCell>
                <TableCell className="font-semibold text-gray-900">{product.name}</TableCell>
                <TableCell>{product.price}</TableCell>
                <TableCell>{product.stock}</TableCell>
                <TableCell>{product.category}</TableCell>
                <TableCell>
                  <span className={`px-2 py-1 rounded-full text-xs font-medium ${statusColors[product.status]}`}>
                    {product.status}
                  </span>
                </TableCell>
                <TableCell className="text-right">
                  <button className="text-sm text-blue-600 hover:text-blue-800">Editar</button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  );
};
