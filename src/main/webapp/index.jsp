<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="employee.crud.db.DBConnection"%>
<%DBConnection.getConnection();%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Servlet, JSP, JDBC and MVC Example</title>

        <%--GOOGLE and bootstrap--%>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/employee.css">
        <%--GOOGLE and bootstrap--%>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="js/employee.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.8.3/angular.min.js" integrity="sha512-KZmyTq3PLx9EZl0RHShHQuXtrvdJ+m35tuOiwlcZfs/rE7NZv29ygNA8SFCkMXTnYZQK2OX0Gm2qKGfvWEtRXA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

        <script>
            var app = angular.module('employeeApp', []);

            app.controller('employeeController', function($scope) {
                $scope.getEmployeeDetails = function (employeeId) {
                    var employeeDetails = [];
                    $.ajax(
                        {
                            url: 'get',
                            type: 'POST',
                            data: {"employeeId": employeeId},
                            async: false,
                            success: function(data, textStatus, jqXHR) {
                                employeeDetails = data;
                                $scope.employee = employeeDetails;
                            },
                            error: function (jqXHR, textStatus, error) {
                                employeeDetails = [];
                                console.log("Error in getting employee details from server " +  error)
                            }
                        }
                    );
                    return $scope.employee;
                }

                document.getElementById("deleteBtn").addEventListener("click", function(event) {
                    event.preventDefault(); // Prevent the default form submission
                    console.log("Click");

                    var deletedEmployees = [];
                    var checkboxes = document.querySelectorAll("input[type='checkbox'].employeeCheckBox:checked");

                    checkboxes.forEach(function(checkbox) {
                        deletedEmployees.push(checkbox.value);
                    });

                    var employeeIds = deletedEmployees.join(",");
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "delete", false);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                    xhr.onreadystatechange = function() {
                        if (xhr.readyState === XMLHttpRequest.DONE) {
                            if (xhr.status === 200) {
                                var response = xhr.responseText;
                                if (response !== "") {
                                    console.log(response);
                                } else {
                                    console.log("Empty response");
                                }
                                window.location.href = "/employee_war_exploded/";
                            } else {
                                console.log("Something went wrong ", xhr.statusText);
                                alert("Exception, errorThrown: " + xhr.statusText);
                            }
                        }
                    };

                    xhr.send("employeeIds=" + encodeURIComponent(employeeIds)); // Adjusted the parameter name
                });

                document.addEventListener('DOMContentLoaded', function() {
                    var currentPage = 1;
                    var totalPages = 5; // Set this to the total number of pages

                    // Function to load a page
                    function loadPage(page) {
                        if (page < 1 || page > totalPages) return;

                        var xhr = new XMLHttpRequest();
                        xhr.open('GET', 'Pagination-servlet?page=' + page, true);

                        xhr.onreadystatechange = function() {
                            if (xhr.readyState === XMLHttpRequest.DONE) {
                                if (xhr.status === 200) {
                                    var data = JSON.parse(xhr.responseText);
                                    updateTable(data.entries);
                                    updatePagination(data.currentPage, data.totalPages, data.totalEntries);
                                } else {
                                    console.error('Something went wrong', xhr.statusText);
                                }
                            }
                        };

                        xhr.send();
                    }

                    // Function to update the table
                    function updateTable(entries) {
                        var tableContainer = document.getElementById('table-container');
                        var tableHtml = '<table class="table table-bordered">';
                        tableHtml += '<thead><tr><th>ID</th><th>Name</th></tr></thead><tbody>';

                        entries.forEach(function(entry) {
                            tableHtml += '<tr><td>' + entry.id + '</td><td>' + entry.name + '</td></tr>';
                        });

                        tableHtml += '</tbody></table>';
                        tableContainer.innerHTML = tableHtml;
                    }

                    // Function to update the pagination
                    function updatePagination(currentPage, totalPages, totalEntries) {
                        var pagination = document.getElementById('pagination');
                        var hintText = document.getElementById('currentEntries');
                        var totalText = document.getElementById('totalEntries');

                        hintText.textContent = currentPage * ENTRIES_PER_PAGE;
                        totalText.textContent = totalEntries;

                        // Clear current pagination
                        while (pagination.firstChild) {
                            pagination.removeChild(pagination.firstChild);
                        }

                        // Add Previous button
                        var prevPageItem = document.createElement('li');
                        prevPageItem.className = 'page-item' + (currentPage === 1 ? ' disabled' : '');
                        var prevLink = document.createElement('a');
                        prevLink.className = 'page-link';
                        prevLink.href = '#';
                        prevLink.textContent = 'Previous';
                        prevLink.addEventListener('click', function(event) {
                            event.preventDefault();
                            loadPage(currentPage - 1);
                        });
                        prevPageItem.appendChild(prevLink);
                        pagination.appendChild(prevPageItem);

                        // Add page number buttons
                        for (var i = 1; i <= totalPages; i++) {
                            var pageItem = document.createElement('li');
                            pageItem.className = 'page-item' + (i === currentPage ? ' active' : '');
                            var pageLink = document.createElement('a');
                            pageLink.className = 'page-link';
                            pageLink.href = '#';
                            pageLink.textContent = i;
                            pageLink.addEventListener('click', function(event) {
                                event.preventDefault();
                                loadPage(parseInt(this.textContent));
                            });
                            pageItem.appendChild(pageLink);
                            pagination.appendChild(pageItem);
                        }

                        // Add Next button
                        var nextPageItem = document.createElement('li');
                        nextPageItem.className = 'page-item' + (currentPage === totalPages ? ' disabled' : '');
                        var nextLink = document.createElement('a');
                        nextLink.className = 'page-link';
                        nextLink.href = '#';
                        nextLink.textContent = 'Next';
                        nextLink.addEventListener('click', function(event) {
                            event.preventDefault();
                            loadPage(currentPage + 1);
                        });
                        nextPageItem.appendChild(nextLink);
                        pagination.appendChild(nextPageItem);
                    }

                    // Initial load
                    loadPage(1);
                });


            })





        </script>
    </head>

    <body ng-app="employeeApp" ng-controller="employeeController">
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-6">
                            <h2>
                                Manage <b>Employees</b>
                            </h2>
                        </div>
                        <div class="col-sm-6">
                            <a href="#addEmployeeModal" class="btn btn-success"
                               data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add
                                    New Employee</span></a> <a href="#deleteEmployeeModal"
                                                               class="btn btn-danger" data-toggle="modal"><i
                                class="material-icons">&#xE15C;</i> <span>Delete</span></a>
                        </div>
                    </div>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><span class="custom-checkbox"> <input
                                type="checkbox" id="selectAll"> <label for="selectAll"></label>
                            </span></th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Phone</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>

                        <c:forEach var="employee" items="${employees}">
                            <tr>
                                <td>
                                    <span class="custom-checkbox">
                                        <input type="checkbox" class="employeeCheckBox" id="${employee.id}" name="options[]"  value="${employee.id}">
                                        <label for="${employee.id}"></label>
                                    </span>
                                </td>
                                <td>${ employee.name }</td>
                                <td>${ employee.email }</td>
                                <td>${ employee.address }</td>
                                <td>${ employee.phone }</td>
                                <td>
                                    <a href="#editEmployeeModal" class="edit" data-toggle="modal">
                                        <i class="material-icons" ng-click="getEmployeeDetails('${employee.id}')" data-toggle="tooltip" title="Edit">&#xE254;</i>
                                    </a>
                                    <a href="#deleteEmployeeModal" class="delete" data-toggle="modal">
                                        <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>



                    </tbody>
                </table>

                <div class="clearfix">
                    <div class="hint-text">
                        Showing <b>5</b> out of <b>25</b> entries
                    </div>
                    <ul class="pagination">
                        <li class="page-item disabled"><a href="#">Previous</a></li>
                        <li class="page-item"><a href="#" class="page-link">1</a></li>
                        <li class="page-item"><a href="#" class="page-link">2</a></li>
                        <li class="page-item active"><a href="#" class="page-link">3</a></li>
                        <li class="page-item"><a href="#" class="page-link">4</a></li>
                        <li class="page-item"><a href="#" class="page-link">5</a></li>
                        <li class="page-item"><a href="#" class="page-link">Next</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- add Modal HTML -->

        <jsp:include page="add.jsp"></jsp:include>

        <!-- Edit Modal HTML -->

        <jsp:include page="update.jsp"></jsp:include>

        <!-- Delete Modal HTML -->

        <jsp:include page="delete.jsp"></jsp:include>

    </body>

</html>
