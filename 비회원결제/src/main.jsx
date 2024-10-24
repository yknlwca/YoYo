import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./App.css";
import { FailPage } from "./pages/Fail";
import { Payment } from "./pages/Payment";
import { Success } from "./pages/Success";
import Home from "./pages/Home";
const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "payment",
    children: [
      {
        path: "checkout/:id",
        element: <Payment />,
      },
      {
        path: "success",
        element: <Success />,
      },
    ],
  },
  {
    path: "fail",
    element: <FailPage />,
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(<RouterProvider router={router} />);
