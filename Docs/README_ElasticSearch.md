## work in progress


For the time being, you can communicate with the es for the following user cases

## Tasks 

1. Return a list of tasks according to status. 
   * _getTasksStatus(String status)_
1. Return a list of tasks according to requester name
   * _getTasksUsername(String profile)_
1. Return a list of task according to requester name AND status name.  
    * _getTasksRequester(String username, String status)_ . 
1. Retunr a list of task according to the bidder username and status.  
    * _getTasksBidded(String username, String status)_ . 
1. Return a task using task id. 
    * _getTasks(Task task)_ 
1. Add a task (same as update a task).  
    * _addTasks(Task task)_.  
1. Update a task (same as add a task).   
    * _addTasks(Task task)_


## Profile 
1. Add a profile, returns true/false on success
    * _addProfile(Profile profile)_
1. Return a profile
    * _getProfile(String username)_
1. See if a profile username already exists, return true/false
    * _profileExists(String username)_
