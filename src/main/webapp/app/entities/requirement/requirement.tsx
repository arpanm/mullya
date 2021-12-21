import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './requirement.reducer';
import { IRequirement } from 'app/shared/model/requirement.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Requirement = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const requirementList = useAppSelector(state => state.requirement.entities);
  const loading = useAppSelector(state => state.requirement.loading);
  const totalItems = useAppSelector(state => state.requirement.totalItems);
  const links = useAppSelector(state => state.requirement.links);
  const entity = useAppSelector(state => state.requirement.entity);
  const updateSuccess = useAppSelector(state => state.requirement.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="requirement-heading" data-cy="RequirementHeading">
        <Translate contentKey="mulyaaApp.requirement.home.title">Requirements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mulyaaApp.requirement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mulyaaApp.requirement.home.createLabel">Create new Requirement</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {requirementList && requirementList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="mulyaaApp.requirement.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('minPrice')}>
                    <Translate contentKey="mulyaaApp.requirement.minPrice">Min Price</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('maxPrice')}>
                    <Translate contentKey="mulyaaApp.requirement.maxPrice">Max Price</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('quantityKg')}>
                    <Translate contentKey="mulyaaApp.requirement.quantityKg">Quantity Kg</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('neededBy')}>
                    <Translate contentKey="mulyaaApp.requirement.neededBy">Needed By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="mulyaaApp.requirement.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="mulyaaApp.requirement.createdOn">Created On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="mulyaaApp.requirement.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="mulyaaApp.requirement.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="mulyaaApp.requirement.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    <Translate contentKey="mulyaaApp.requirement.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mulyaaApp.requirement.buyerAddress">Buyer Address</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mulyaaApp.requirement.buyerUser">Buyer User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mulyaaApp.requirement.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mulyaaApp.requirement.variant">Variant</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mulyaaApp.requirement.subVariant">Sub Variant</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {requirementList.map((requirement, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${requirement.id}`} color="link" size="sm">
                        {requirement.id}
                      </Button>
                    </td>
                    <td>{requirement.minPrice}</td>
                    <td>{requirement.maxPrice}</td>
                    <td>{requirement.quantityKg}</td>
                    <td>{requirement.neededBy}</td>
                    <td>{requirement.description}</td>
                    <td>
                      {requirement.createdOn ? (
                        <TextFormat type="date" value={requirement.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{requirement.createdBy}</td>
                    <td>
                      {requirement.updatedOn ? (
                        <TextFormat type="date" value={requirement.updatedOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{requirement.updatedBy}</td>
                    <td>
                      <Translate contentKey={`mulyaaApp.RequirementStatus.${requirement.status}`} />
                    </td>
                    <td>
                      {requirement.buyerAddress ? (
                        <Link to={`address/${requirement.buyerAddress.id}`}>{requirement.buyerAddress.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{requirement.buyerUser ? <Link to={`user/${requirement.buyerUser.id}`}>{requirement.buyerUser.id}</Link> : ''}</td>
                    <td>
                      {requirement.category ? <Link to={`catalogue/${requirement.category.id}`}>{requirement.category.id}</Link> : ''}
                    </td>
                    <td>{requirement.variant ? <Link to={`catalogue/${requirement.variant.id}`}>{requirement.variant.id}</Link> : ''}</td>
                    <td>
                      {requirement.subVariant ? <Link to={`catalogue/${requirement.subVariant.id}`}>{requirement.subVariant.id}</Link> : ''}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${requirement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${requirement.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${requirement.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="mulyaaApp.requirement.home.notFound">No Requirements found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Requirement;
