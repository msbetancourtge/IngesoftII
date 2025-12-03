import {Table, TableCaption, TableHeader, TableRow, TableHead, TableBody, TableCell } from "@/components/ui/table"

export const AdminProductsPage = () => {
  return (
    <>
      
      <Table>
        <TableCaption>A list of your recent invoices.</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead className="w-[100px]">ID</TableHead>
            <TableHead>Imagen</TableHead>
            <TableHead>Nombre</TableHead>
            <TableHead>Precio</TableHead>
            <TableHead>Inventario</TableHead>
            <TableHead>Categoria</TableHead>
            <TableHead className="text-right">Acciones</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          <TableRow>
            <TableCell className="font-medium">001</TableCell>
            <TableCell>
              <img src="" alt="product" className="w-20 h-20 object-cover rounded-md" />
            </TableCell>
            <TableCell>Producto 1</TableCell>
            <TableCell>$250.00</TableCell>
            <TableCell >100</TableCell>
            <TableCell >Bebidas</TableCell>
            <TableCell className="text-right">Editar</TableCell>
          </TableRow>
        </TableBody>
      </Table>

    
    </>
  )
}
