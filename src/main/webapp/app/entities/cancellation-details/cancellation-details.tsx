import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './cancellation-details.reducer';
import { ICancellationDetails } from 'app/shared/model/cancellation-details.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CancellationDetails = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const cancellationDetailsList = useAppSelector(state => state.cancellationDetails.entities);
  const loading = useAppSelector(state => state.cancellationDetails.loading);
  const totalItems = useAppSelector(state => state.cancellationDetails.totalItems);
  const links = useAppSelector(state => state.cancellationDetails.links);
  const entity = useAppSelector(state => state.cancellationDetails.entity);
  const updateSuccess = useAppSelector(state => state.cancellationDetails.updateSuccess);

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
      <h2 id="cancellation-details-heading" data-cy="CancellationDetailsHeading">
        <Translate contentKey="mulyaaApp.cancellationDetails.home.title">Cancellation Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mulyaaApp.cancellationDetails.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mulyaaApp.cancellationDetails.home.createLabel">Create new Cancellation Details</Translate>
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
          {cancellationDetailsList && cancellationDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cancelationType')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.cancelationType">Cancelation Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cancellationReason')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.cancellationReason">Cancellation Reason</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cancellationDate')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.cancellationDate">Cancellation Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cancellationTime')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.cancellationTime">Cancellation Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('refundId')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.refundId">Refund Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cancellationStatus')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.cancellationStatus">Cancellation Status</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.createdOn">Created On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="mulyaaApp.cancellationDetails.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mulyaaApp.cancellationDetails.order">Order</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mulyaaApp.cancellationDetails.approver">Approver</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mulyaaApp.cancellationDetails.initiator">Initiator</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {cancellationDetailsList.map((cancellationDetails, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${cancellationDetails.id}`} color="link" size="sm">
                        {cancellationDetails.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`mulyaaApp.CancelationType.${cancellationDetails.cancelationType}`} />
                    </td>
                    <td>{cancellationDetails.cancellationReason}</td>
                    <td>{cancellationDetails.cancellationDate}</td>
                    <td>
                      {cancellationDetails.cancellationTime ? (
                        <TextFormat type="date" value={cancellationDetails.cancellationTime} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{cancellationDetails.refundId}</td>
                    <td>
                      <Translate contentKey={`mulyaaApp.CancellationStatus.${cancellationDetails.cancellationStatus}`} />
                    </td>
                    <td>
                      {cancellationDetails.createdOn ? (
                        <TextFormat type="date" value={cancellationDetails.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{cancellationDetails.createdBy}</td>
                    <td>
                      {cancellationDetails.updatedOn ? (
                        <TextFormat type="date" value={cancellationDetails.updatedOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{cancellationDetails.updatedBy}</td>
                    <td>
                      {cancellationDetails.order ? (
                        <Link to={`order/${cancellationDetails.order.id}`}>{cancellationDetails.order.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {cancellationDetails.approver ? (
                        <Link to={`user/${cancellationDetails.approver.id}`}>{cancellationDetails.approver.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {cancellationDetails.initiator ? (
                        <Link to={`user/${cancellationDetails.initiator.id}`}>{cancellationDetails.initiator.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`${match.url}/${cancellationDetails.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${cancellationDetails.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${cancellationDetails.id}/delete`}
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
                <Translate contentKey="mulyaaApp.cancellationDetails.home.notFound">No Cancellation Details found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default CancellationDetails;
