<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div id="editEmployeeModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="update" method="POST" id="update-form">
        <div class="modal-header">
          <h4 class="modal-title">Edit Employee</h4>
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>Name</label>
            <input type="text" ng-model="updateForm.name" class="form-control" required>
          </div>
          <div class="form-group">
            <label>Email</label>
            <input type="email" ng-model="updateForm.email" class="form-control" required>
          </div>
          <div class="form-group">
            <label>Address</label>
            <textarea class="form-control" ng-model="updateForm.address" required></textarea>
          </div>
          <div class="form-group">
            <label>Phone</label>
            <input type="text" ng-model="updateForm.phone" class="form-control" required>
          </div>
        </div>
        <div class="modal-footer">
          <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
          <input type="submit" class="btn btn-info" value="Save">
        </div>
      </form>
    </div>
  </div>
</div>