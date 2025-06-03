<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="assets/img/logo.png">
    <link rel="icon" type="image/png" href="assets/img/favicon.ico">
    <title>
        Quản lý người dùng
    </title>
    <!--     Fonts and icons     -->
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Oswald:wght@500;600;700&family=Pacifico&display=swap"
          rel="stylesheet">
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <!-- Nucleo Icons -->
    <link href="css/nucleo-icons.css" rel="stylesheet"/>
    <!-- CSS Files -->
    <link href="css/black-dashboard.css?v=1.0.0" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body data-bs-theme="dark">
<div class="wrapper">
    <div class="sidebar">
        <div class="sidebar-wrapper">
            <div class="logo">
                <a href="../profile.jsp" class="simple-text logo-normal">
                    Xin chào <%= session.getAttribute("handle") %>
                </a>
            </div>
            <ul class="nav">
                <li>
                    <a href="adminDashboard.jsp">
                        <i class="tim-icons icon-chart-pie-36"></i>
                        <p>Bảng Điều khiển</p>
                    </a>
                </li>
                <li class="active ">
                    <a href="userManagement.jsp">
                        <i class="tim-icons icon-badge"></i>
                        <p>Quản lý người dùng</p>
                    </a>
                </li>
                <li>
                    <a href="orderManagement.jsp">
                        <i class="tim-icons icon-cart"></i>
                        <p>Quản lý đơn hàng</p>
                    </a>
                </li>
                <li>
                    <a href="productManagement.jsp">
                        <i class="tim-icons icon-paper"></i>
                        <p>Quản lý sản phẩm</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="main-panel">
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-absolute navbar-transparent">
            <div class="container-fluid">
                <div class="navbar-wrapper">
                    <div class="navbar-toggle d-inline">
                        <button type="button" class="navbar-toggler">
                            <span class="navbar-toggler-bar bar1"></span>
                            <span class="navbar-toggler-bar bar2"></span>
                            <span class="navbar-toggler-bar bar3"></span>
                        </button>
                    </div>
                    <a class="navbar-brand" href="javascript:void(0)">Slowly Cake Admin</a>
                </div>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navigation"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-bar navbar-kebab"></span>
                    <span class="navbar-toggler-bar navbar-kebab"></span>
                    <span class="navbar-toggler-bar navbar-kebab"></span>
                </button>
                <div class="collapse navbar-collapse" id="navigation">
                    <ul class="navbar-nav ml-auto">
                        <li class="dropdown nav-item">
                            <a href="#" class="dropdown-toggle nav-link" data-toggle="dropdown">
                                <div class="photo">
                                    <img src="img/review2.png" alt="Profile Photo">
                                </div>
                                <p class="d-lg-none">Log out
                                </p>
                            </a>
                            <ul class="dropdown-menu dropdown-navbar">
                                <li class="nav-link">
                                    <a href="../profile.jsp" class="nav-item dropdown-item">Profile
                                    </a>
                                </li>
                                <li class="dropdown-divider"></li>
                                <li class="nav-link">
                                    <a href="../logout" class="nav-item dropdown-item">Đăng xuất
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="separator d-lg-none"></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- End Navbar -->
        <div class="content bg-dark text-white">
            <div class="row">
                <div class="col-md-12">
                    <div class="card ">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h4 class="card-title"> Quản lý người dùng</h4>
                            <button id="addBtn" class="btn btn-success">
                                <i class="tim-icons icon-simple-add"></i>
                            </button>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="usersTable" class="display">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên đăng nhập</th>
                                        <th>Tên người dùng</th>
                                        <th>Email</th>
                                        <th>SĐT</th>
                                        <th>Địa chỉ</th>
                                        <th>Loại</th>
                                        <th>Thao tác</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal để chỉnh sửa thông tin -->
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" inert>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalLabel">Sửa thông tin</h5>
            </div>
            <div class="modal-body">
                <form id="editUserForm">
                    <div class="form-group">
                        <label for="editHandle">Username:</label>
                        <input type="text" id="editHandle" name="handle" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="editName">Tên người dùng:</label>
                        <input type="text" id="editName" name="name" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="editEmail">Email:</label>
                        <input type="email" id="editEmail" name="email" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="editPhoneNum">Số điện thoại:</label>
                        <input type="text" id="editPhoneNum" name="phoneNum" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="editAddress">Địa chỉ:</label>
                        <input type="text" id="editAddress" name="address" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!--   Core JS Files   -->
<script src="js/core/jquery.min.js"></script>
<script src="js/core/popper.min.js"></script>
<script src="js/core/bootstrap.min.js"></script>
<script src="js/plugins/perfect-scrollbar.jquery.min.js"></script>
<!-- Chart JS -->
<script src="js/plugins/chartjs.min.js"></script>
<!--  Notifications Plugin    -->
<script src="js/plugins/bootstrap-notify.js"></script>
<!-- Control Center for Black Dashboard: parallax effects, scripts for the example pages etc -->
<script src="js/black-dashboard.min.js?v=1.0.0"></script>
<!-- Black Dashboard DEMO methods, don't include it in your project! -->
<script src="demo/demo.js"></script>
<script>
    $(document).ready(function () {
        $().ready(function () {
            $sidebar = $('.sidebar');
            $navbar = $('.navbar');
            $main_panel = $('.main-panel');

            $full_page = $('.full-page');

            $sidebar_responsive = $('body > .navbar-collapse');
            sidebar_mini_active = true;
            white_color = false;

            window_width = $(window).width();

            fixed_plugin_open = $('.sidebar .sidebar-wrapper .nav li.active a p').html();


            $('.fixed-plugin a').click(function (event) {
                if ($(this).hasClass('switch-trigger')) {
                    if (event.stopPropagation) {
                        event.stopPropagation();
                    } else if (window.event) {
                        window.event.cancelBubble = true;
                    }
                }
            });

            $('.fixed-plugin .background-color span').click(function () {
                $(this).siblings().removeClass('active');
                $(this).addClass('active');

                var new_color = $(this).data('color');

                if ($sidebar.length != 0) {
                    $sidebar.attr('data', new_color);
                }

                if ($main_panel.length != 0) {
                    $main_panel.attr('data', new_color);
                }

                if ($full_page.length != 0) {
                    $full_page.attr('filter-color', new_color);
                }

                if ($sidebar_responsive.length != 0) {
                    $sidebar_responsive.attr('data', new_color);
                }
            });

            $('.switch-sidebar-mini input').on("switchChange.bootstrapSwitch", function () {
                var $btn = $(this);

                if (sidebar_mini_active == true) {
                    $('body').removeClass('sidebar-mini');
                    sidebar_mini_active = false;
                    blackDashboard.showSidebarMessage('Sidebar mini deactivated...');
                } else {
                    $('body').addClass('sidebar-mini');
                    sidebar_mini_active = true;
                    blackDashboard.showSidebarMessage('Sidebar mini activated...');
                }

                // we simulate the window Resize so the charts will get updated in realtime.
                var simulateWindowResize = setInterval(function () {
                    window.dispatchEvent(new Event('resize'));
                }, 180);

                // we stop the simulation of Window Resize after the animations are completed
                setTimeout(function () {
                    clearInterval(simulateWindowResize);
                }, 1000);
            });

            $('.switch-change-color input').on("switchChange.bootstrapSwitch", function () {
                var $btn = $(this);

                if (white_color == true) {

                    $('body').addClass('change-background');
                    setTimeout(function () {
                        $('body').removeClass('change-background');
                        $('body').removeClass('white-content');
                    }, 900);
                    white_color = false;
                } else {

                    $('body').addClass('change-background');
                    setTimeout(function () {
                        $('body').removeClass('change-background');
                        $('body').addClass('white-content');
                    }, 900);

                    white_color = true;
                }


            });

            $('.light-badge').click(function () {
                $('body').addClass('white-content');
            });

            $('.dark-badge').click(function () {
                $('body').removeClass('white-content');
            });
        });
    });
</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    $(document).ready(function () {
        function loadUsers() {
            $.get("userManagementServlet", function (data) {
                let table = $('#usersTable').DataTable();
                table.clear();

                data.forEach(user => {
                    table.row.add([
                        user.id,
                        user.handle,
                        user.name,
                        user.email,
                        user.phoneNum,
                        user.address,
                        user.role == 1 ? "Admin" : "User",
                        `<button class="btn btn-success btn-sm edit-btn"
                        data-id="${user.id}"
                        data-handle="${user.handle}"
                        data-name="${user.name}"
                        data-email="${user.email}"
                        data-phonenum="${user.phoneNum}"
                        data-address="${user.address}"
                        data-role="${user.role}">
                        <i class="tim-icons icon-pencil"></i>
                    </button>
                    <button class="btn btn-danger btn-sm delete-btn" data-id="${user.id}">
                        <i class="tim-icons icon-simple-delete"></i>
                    </button>`
                    ]).draw();
                });

                $(".edit-btn").click(function () {
                    $("#editId").val($(this).data("id"));
                    $("#editHandle").val($(this).data("handle"));
                    $("#editName").val($(this).data("name"));
                    $("#editEmail").val($(this).data("email"));
                    $("#editPhoneNum").val($(this).data("phonenum"));
                    $("#editAddress").val($(this).data("address"));
                    $("#editRoleHidden").val($(this).data("role"));

                    $("#editModal").modal("show");
                });

                $(".delete-btn").click(function () {
                    let userId = $(this).data("id");
                    if (confirm("Bạn có muốn xóa người dùng này?")) {
                        $.ajax({
                            url: "/deleteUser",
                            type: "POST",
                            data: {id: userId},
                            success: function (response) {
                                alert(response);
                                loadUsers();
                            }
                        });
                    }
                });
            });
        }

        $("#editUserForm").submit(function (e) {
            e.preventDefault();

            let userData = {
                id: $("#editId").val(),
                handle: $("#editHandle").val(),
                name: $("#editName").val(),
                email: $("#editEmail").val(),
                phoneNum: $("#editPhoneNum").val(),
                address: $("#editAddress").val(),
                role: $("#editRoleHidden").val()
            };

            $.post("${pageContext.request.contextPath}/editUser", userData, function (response) {
                alert(response);
                $("#editModal").modal("hide");
                loadUsers();
            });
        });

        $('#usersTable').DataTable();
        loadUsers();
    });
</script>

<script>
    $(document).ready(function () {
        // 🛠️ Handle form submission for updating user info
        $("#editUserForm").submit(function (e) {
            e.preventDefault(); // Prevent page reload

            let userData = {
                handle: $("#editHandle").val().trim(),
                name: $("#editName").val().trim(),
                email: $("#editEmail").val().trim(),
                phoneNum: $("#editPhoneNum").val().trim(),
                address: $("#editAddress").val().trim()
            };

            console.log("🔹 Sending JSON:", JSON.stringify(userData)); // Debugging

            $.ajax({
                url: "editUser", // Ensure this matches your servlet mapping
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(userData),
                dataType: "json",
                success: function (response) {
                    console.log("✅ Server Response:", response);

                    alert(response.message);
                    if (response.success) {
                        $("#editModal").modal("hide");
                        location.reload(); // Refresh the table to show updated data
                    }
                },
                error: function (xhr) {
                    console.error("❌ Error response:", xhr.responseText);
                    alert("Lỗi khi gửi dữ liệu: " + xhr.responseText);
                }
            });
        });

        // 🛠️ Open modal with pre-filled user info when clicking edit button
        $(document).on("click", ".edit-btn", function () {
            $("#editHandle").val($(this).data("handle"));
            $("#editName").val($(this).data("name"));
            $("#editEmail").val($(this).data("email"));
            $("#editPhoneNum").val($(this).data("phonenum"));
            $("#editAddress").val($(this).data("address"));

            // Ensure modal is interactive
            $("body > *:not(#editModal)").attr("inert", "true");
            $("#editModal").removeAttr("inert").modal("show");
        });

        // 🛠️ Restore accessibility when modal is closed
        $("#editModal").on("hidden.bs.modal", function () {
            $("body > *:not(#editModal)").removeAttr("inert");
        });

        // 🛠️ Auto-focus first input field when modal opens
        $("#editModal").on("shown.bs.modal", function () {
            $("#editHandle").focus();
        });
    });
</script>

</body>

</html>