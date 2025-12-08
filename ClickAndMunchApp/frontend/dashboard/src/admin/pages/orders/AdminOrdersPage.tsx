import { useMemo, useState } from "react";
import { Table, TableBody, TableCaption, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";

type OrderStatus = "Preparing" | "Ready" | "Served" | "Delivered" | "Cancelled";

interface AdminOrder {
  id: string;
  customer: string;
  restaurant: string;
  eta: string;
  status: OrderStatus;
  total: string;
  createdAt: string;
  channel: "Reservation" | "In-person";
}

const statusColors: Record<OrderStatus, string> = {
  Preparing: "bg-amber-100 text-amber-800",
  Ready: "bg-blue-100 text-blue-800",
  Served: "bg-green-100 text-green-800",
  Delivered: "bg-emerald-100 text-emerald-800",
  Cancelled: "bg-red-100 text-red-800",
};

const ordersSeed: AdminOrder[] = [
  {
    id: "ORD-9001",
    customer: "Ana Ríos",
    restaurant: "Urban Bistro",
    eta: "12 min",
    status: "Preparing",
    total: "$48.20",
    createdAt: "2025-10-21 12:05",
    channel: "Reservation",
  },
  {
    id: "ORD-9002",
    customer: "Carlos Mendez",
    restaurant: "Café Andino",
    eta: "Listo",
    status: "Ready",
    total: "$28.50",
    createdAt: "2025-10-21 12:10",
    channel: "Reservation",
  },
  {
    id: "ORD-9003",
    customer: "John Doe",
    restaurant: "Urban Bistro",
    eta: "Entregado",
    status: "Delivered",
    total: "$16.90",
    createdAt: "2025-10-21 11:40",
    channel: "In-person",
  },
  {
    id: "ORD-9004",
    customer: "Emily Park",
    restaurant: "Café Andino",
    eta: "Servido",
    status: "Served",
    total: "$54.00",
    createdAt: "2025-10-21 11:55",
    channel: "Reservation",
  },
  {
    id: "ORD-9005",
    customer: "Miguel Soto",
    restaurant: "Urban Bistro",
    eta: "Cancelado",
    status: "Cancelled",
    total: "$0.00",
    createdAt: "2025-10-21 11:20",
    channel: "Reservation",
  },
];

export const AdminOrdersPage = () => {
  const [statusFilter, setStatusFilter] = useState<OrderStatus | "all">("all");
  const [channelFilter, setChannelFilter] = useState<AdminOrder["channel"] | "all">("all");
  const [search, setSearch] = useState("");

  const filtered = useMemo(() => {
    return ordersSeed.filter((order) => {
      const matchesStatus = statusFilter === "all" || order.status === statusFilter;
      const matchesChannel = channelFilter === "all" || order.channel === channelFilter;
      const q = search.trim().toLowerCase();
      const matchesSearch =
        q.length === 0 ||
        order.id.toLowerCase().includes(q) ||
        order.customer.toLowerCase().includes(q) ||
        order.restaurant.toLowerCase().includes(q);
      return matchesStatus && matchesChannel && matchesSearch;
    });
  }, [statusFilter, channelFilter, search]);

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-2">
        <h1 className="text-2xl font-bold text-gray-900">Órdenes</h1>
        <p className="text-gray-600">
          Control de órdenes ligadas a reservas (requerimiento: vincular pedido a la reserva y permitir seguimiento de estado).
        </p>
      </div>

      <div className="bg-white border border-gray-200 rounded-xl p-4 shadow-sm space-y-4">
        <div className="flex flex-col md:flex-row gap-3 md:items-center md:justify-between">
          <input
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Buscar por ID, cliente o restaurante..."
            className="w-full md:w-1/2 rounded-lg border border-gray-200 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <div className="flex gap-2">
            <select
              value={statusFilter}
              onChange={(e) => setStatusFilter(e.target.value as OrderStatus | "all")}
              className="rounded-lg border border-gray-200 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="all">Todos los estados</option>
              {["Preparing", "Ready", "Served", "Delivered", "Cancelled"].map((s) => (
                <option key={s} value={s}>
                  {s}
                </option>
              ))}
            </select>
            <select
              value={channelFilter}
              onChange={(e) => setChannelFilter(e.target.value as AdminOrder["channel"] | "all")}
              className="rounded-lg border border-gray-200 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="all">Todos los canales</option>
              <option value="Reservation">Reserva</option>
              <option value="In-person">En sitio</option>
            </select>
          </div>
        </div>

        <Table>
          <TableCaption>Seguimiento de órdenes y estados de cocina.</TableCaption>
          <TableHeader>
            <TableRow>
              <TableHead>ID</TableHead>
              <TableHead>Cliente</TableHead>
              <TableHead>Restaurante</TableHead>
              <TableHead>Canal</TableHead>
              <TableHead>ETA</TableHead>
              <TableHead>Estado</TableHead>
              <TableHead>Total</TableHead>
              <TableHead>Creado</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {filtered.map((order) => (
              <TableRow key={order.id}>
                <TableCell className="font-medium">{order.id}</TableCell>
                <TableCell>{order.customer}</TableCell>
                <TableCell>{order.restaurant}</TableCell>
                <TableCell>{order.channel}</TableCell>
                <TableCell>{order.eta}</TableCell>
                <TableCell>
                  <span className={`px-2 py-1 rounded-full text-xs font-medium ${statusColors[order.status]}`}>
                    {order.status}
                  </span>
                </TableCell>
                <TableCell>{order.total}</TableCell>
                <TableCell>{order.createdAt}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  );
};

