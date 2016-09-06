function create_folder() {
  createFolder=$1;
  # Try SVN create
  if [ ! -d "$createFolder" ]; then
    echo "Create folder $createFolder (SVN)";
    svn mkdir --parents "$createFolder" > /dev/null;
  fi
  # If folder still not exists, it is not versioned, so try a normal mkdir
  if [ ! -d "$folder" ]; then
    echo "Create folder $createFolder (w/o SVN)";
    mkdir "$createFolder" > /dev/null;
  fi
}

function move_folder() {
  oldFolder=$1;
  newFolder=$2;
  # Try SVN move
  if [ -d "$oldFolder" ]; then
    echo "Moving folder $oldFolder (SVN)";
    svn mv "$oldFolder" "$newFolder" &>/dev/null;
  fi
  # If folder still exists, it is not versioned, so try a normal move
  if [ -d "$oldFolder" ]; then
    echo "Moving folder $oldFOlder (w/o SVN)";
    mv "$oldFolder" "$newFolder" > /dev/null;
  fi
}

function remove_folder() {
  removeFolder=$1;
  # Try SVN remove
  if [ -d "$removeFolder" ]; then
    svn rm "$removeFolder" &> /dev/null;
  fi
  # If folder still exists, it is not versioned, so try a normal remove
  if [ -d "$removeFolder" ]; then
    rm -r "$removeFolder" > /dev/null;
  fi
}

function migrate_project() { 
  project=$1
  # Move edu folders
  EDUFOLDERS=$(find $project -type d -name "edu" -not -path "*bin*");
  for folder in $EDUFOLDERS
  do
    create_folder "$folder/../tools";
    move_folder "$folder/kit/ipd/sdq/vitruvius" "$folder/../tools/";
    #  Remove the old folders. Some may not be versioned (e.g. xtend-gen), so they have to be removed without svn
    remove_folder "$folder/kit/ipd/sdq/vitruvius/";
    remove_folder "$folder/kit/ipd/sdq/";
    remove_folder "$folder/kit/ipd/";
    remove_folder "$folder/kit/";
    remove_folder "$folder";
  done

  # Replace references in files
  find $project -type f -not -name "*.class" -not -name "*._trace" -not -name "*.xtendbin" -not -name "*.jar" -not -name "*.zip" -not -name "*.resource" | while read file
  do
    echo "Update file $file";
    sed -i "s/edu.kit.ipd.sdq.vitruvius/tools.vitruvius/g" "$file";
  done
 
  # Rename project
  echo "Rename project $project";
  newProjectName=$(echo "$project" | sed 's/edu.kit.ipd.sdq.vitruvius/tools.vitruvius/g');
  move_folder "$project" "$newProjectName";

  find $newProjectName -name "*edu.kit.ipd.sdq.vitruvius*" -not -name "$newProjectName" | while read renameFile
  do
    echo "Rename file $renameFile";
    newFileName=$(echo "$renameFile" | sed 's/edu.kit.ipd.sdq.vitruvius/tools.vitruvius/g');
    svn mv "$renameFile" "$newFileName" > /dev/null;
  done
}

# Migrate the namespaces of each project in the current folder
for project in $(ls .); do   
  echo "------------------------";   
  echo "Migrate project $project";   
  migrate_project $project; 
done
